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

const WorkflowEditor = () => {
  // Function to handle prompt text changes
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

  // Initial nodes setup using the custom PromptNode
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

  // Registering custom node types
  const nodeTypes = useMemo(() => ({ promptNode: PromptNode }), []);

  const onNodesChange = useCallback(
    (changes: NodeChange[]) => setNodes((nds) => applyNodeChanges(changes, nds)),
    []
  );

  const onEdgesChange = useCallback(
    (changes: EdgeChange[]) => setEdges((eds) => applyEdgeChanges(changes, eds)),
    []
  );

  // Allow users to connect nodes manually
  const onConnect = useCallback(
    (connection: Connection) => setEdges((eds) => addEdge(connection, eds)),
    []
  );

  return (
    <div className="flex flex-col w-full h-full bg-gray-50">
      <div className="p-4 bg-white shadow-sm border-b">
        <h1 className="text-xl font-bold text-gray-800">AI Decision Flow Editor</h1>
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