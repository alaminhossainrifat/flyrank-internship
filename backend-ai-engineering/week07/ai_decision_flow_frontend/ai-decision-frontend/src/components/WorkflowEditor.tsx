import { useState, useCallback, useMemo } from 'react';
import ReactFlow, { 
  Background, 
  Controls, 
  applyNodeChanges, 
  applyEdgeChanges,
  addEdge,
  type Node,
  type Edge,
  type NodeChange,
  type EdgeChange,
  type Connection
} from 'reactflow';
import 'reactflow/dist/style.css';
import PromptNode from './nodes/PromptNode';
import axios from 'axios';

const WorkflowEditor = () => {
  const updateNodePrompt = useCallback((id: string, newPrompt: string) => {
    setNodes((nds) =>
      nds.map((node) => {
        if (node.id === id) {
          return {
            ...node,
            data: { ...node.data, prompt: newPrompt },
          };
        }
        return node;
      })
    );
  }, []);

  const initialNodes: Node[] = [
    {
      id: '1',
      type: 'promptNode',
      data: { 
        label: 'Step 1: Check Request Type', 
        prompt: 'Is this a technical support request?',
        onChange: updateNodePrompt
      },
      position: { x: 250, y: 50 },
    },
    {
      id: '2',
      type: 'promptNode',
      data: { 
        label: 'Step 2: Support Logic', 
        prompt: 'Is the device turning on?',
        onChange: updateNodePrompt
      },
      position: { x: 100, y: 300 },
    },
    {
      id: '3',
      type: 'promptNode',
      data: { 
        label: 'Step 2: Sales Logic', 
        prompt: 'Does the customer want to buy a laptop?',
        onChange: updateNodePrompt
      },
      position: { x: 450, y: 300 },
    }
  ];

  const initialEdges: Edge[] = [
    { id: 'e1-yes-2', source: '1', sourceHandle: 'yes', target: '2', animated: true, style: { stroke: '#22c55e' } },
    { id: 'e1-no-3', source: '1', sourceHandle: 'no', target: '3', animated: true, style: { stroke: '#ef4444' } }
  ];

  const [nodes, setNodes] = useState<Node[]>(initialNodes);
  const [edges, setEdges] = useState<Edge[]>(initialEdges);
  const [isExecuting, setIsExecuting] = useState(false);

  const nodeTypes = useMemo(() => ({ promptNode: PromptNode }), []);

  const onNodesChange = useCallback(
    (changes: NodeChange[]) => setNodes((nds) => applyNodeChanges(changes, nds)),
    []
  );

  const onEdgesChange = useCallback(
    (changes: EdgeChange[]) => setEdges((eds) => applyEdgeChanges(changes, eds)),
    []
  );

  const onConnect = useCallback(
    (connection: Connection) => setEdges((eds) => addEdge(connection, eds)),
    []
  );

  /**
   * Executes the workflow starting from Node 1
   */
  const runWorkflow = async () => {
    setIsExecuting(true);
    let currentNodeId: string | undefined = '1';

    while (currentNodeId) {
      const currentNode = nodes.find((n) => n.id === currentNodeId);
      if (!currentNode) break;

      try {
        // Send the prompt to Spring Boot Backend
        const response = await axios.post('http://localhost:8080/api/decision/evaluate', {
          prompt: currentNode.data.prompt
        });

        const decision = response.data.decision;
        alert(`Evaluating [${currentNode.data.label}]\nAI Decision: ${decision}`);

        // Find the next edge based on YES/NO response
        const nextEdge = edges.find(
          (e) => e.source === currentNodeId &&
                 ((decision === 'YES' && e.sourceHandle === 'yes') ||
                  (decision === 'NO' && e.sourceHandle === 'no'))
        );

        // Move to the next node, or end if no connection exists
        currentNodeId = nextEdge ? nextEdge.target : undefined;

      } catch (error) {
        console.error("API Error:", error);
        alert("Failed to connect to the backend. Is Spring Boot running?");
        break;
      }
    }
    
    alert("Workflow Execution Completed!");
    setIsExecuting(false);
  };

  return (
    <div className="flex flex-col w-full h-full bg-gray-50">
      <div className="flex items-center justify-between p-4 bg-white shadow-sm border-b z-10">
        <h1 className="text-xl font-bold text-gray-800">AI Decision Flow Editor</h1>
        <button 
          onClick={runWorkflow}
          disabled={isExecuting}
          className={`px-4 py-2 font-bold text-white rounded shadow ${isExecuting ? 'bg-gray-400' : 'bg-blue-600 hover:bg-blue-700'}`}
        >
          {isExecuting ? 'Running...' : 'Run Workflow 🚀'}
        </button>
      </div>
      
      <div className="flex-grow w-full h-full">
        <ReactFlow
          nodes={nodes}
          edges={edges}
          nodeTypes={nodeTypes}
          onNodesChange={onNodesChange}
          onEdgesChange={onEdgesChange}
          onConnect={onConnect}
          fitView
        >
          <Background color="#ccc" gap={16} />
          <Controls />
        </ReactFlow>
      </div>
    </div>
  );
};

export default WorkflowEditor;