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

// Type definition for Execution Logs
type LogType = {
  id: number;
  timestamp: string;
  message: string;
  type: 'info' | 'success' | 'error' | 'warning';
};

const WorkflowEditor = () => {
  const [logs, setLogs] = useState<LogType[]>([]);
  const [isExecuting, setIsExecuting] = useState(false);

  // Helper to add logs to the panel
  const addLog = (message: string, type: 'info' | 'success' | 'error' | 'warning' = 'info') => {
    const newLog: LogType = {
      id: Date.now(),
      timestamp: new Date().toLocaleTimeString(),
      message,
      type
    };
    setLogs((prev) => [...prev, newLog]);
  };

  const updateNodePrompt = useCallback((id: string, newPrompt: string) => {
    setNodes((nds) =>
      nds.map((node) => {
        if (node.id === id) {
          return { ...node, data: { ...node.data, prompt: newPrompt } };
        }
        return node;
      })
    );
  }, []);

  const initialNodes: Node[] = [
    {
      id: '1',
      type: 'promptNode',
      data: { label: 'Step 1: Check Request', prompt: 'Is this a technical support request?', onChange: updateNodePrompt, isActive: false },
      position: { x: 250, y: 50 },
    },
    {
      id: '2',
      type: 'promptNode',
      data: { label: 'Step 2: Support Logic', prompt: 'Is the device turning on?', onChange: updateNodePrompt, isActive: false },
      position: { x: 50, y: 350 },
    },
    {
      id: '3',
      type: 'promptNode',
      data: { label: 'Step 2: Sales Logic', prompt: 'Does the customer want to buy a laptop?', onChange: updateNodePrompt, isActive: false },
      position: { x: 450, y: 350 },
    }
  ];

  const initialEdges: Edge[] = [
    { id: 'e1-yes-2', source: '1', sourceHandle: 'yes', target: '2', animated: false, style: { stroke: '#9ca3af', strokeWidth: 2 } },
    { id: 'e1-no-3', source: '1', sourceHandle: 'no', target: '3', animated: false, style: { stroke: '#9ca3af', strokeWidth: 2 } }
  ];

  const [nodes, setNodes] = useState<Node[]>(initialNodes);
  const [edges, setEdges] = useState<Edge[]>(initialEdges);
  const nodeTypes = useMemo(() => ({ promptNode: PromptNode }), []);

  const onNodesChange = useCallback((changes: NodeChange[]) => setNodes((nds) => applyNodeChanges(changes, nds)), []);
  const onEdgesChange = useCallback((changes: EdgeChange[]) => setEdges((eds) => applyEdgeChanges(changes, eds)), []);
  const onConnect = useCallback((connection: Connection) => setEdges((eds) => addEdge(connection, eds)), []);

  // Helper to add a delay for visual effect
  const sleep = (ms: number) => new Promise((r) => setTimeout(r, ms));

  const runWorkflow = async () => {
    setIsExecuting(true);
    setLogs([]);
    addLog("Workflow execution started...", "info");
    
    // Reset all nodes and edges to default state
    setNodes((nds) => nds.map((n) => ({ ...n, data: { ...n.data, isActive: false } })));
    setEdges((eds) => eds.map((e) => ({ ...e, animated: false, style: { ...e.style, stroke: '#9ca3af', strokeWidth: 2 } })));

    let currentNodeId: string | undefined = '1';

    while (currentNodeId) {
      const currentNode = nodes.find((n) => n.id === currentNodeId);
      if (!currentNode) break;

      // Highlight current node
      setNodes((nds) => nds.map((n) => n.id === currentNodeId ? { ...n, data: { ...n.data, isActive: true } } : n));
      addLog(`Executing Node: [${currentNode.data.label}]`, "warning");
      
      await sleep(1000); // Visual delay

      try {
        const response = await axios.post('http://localhost:8080/api/decision/evaluate', {
          prompt: currentNode.data.prompt
        });

        const decision = response.data.decision;
        addLog(`AI Response: ${decision}`, decision === 'YES' ? 'success' : 'error');

        // Identify the path taken
        const nextEdge = edges.find(
          (e) => e.source === currentNodeId &&
                 ((decision === 'YES' && e.sourceHandle === 'yes') ||
                  (decision === 'NO' && e.sourceHandle === 'no'))
        );

        if (nextEdge) {
          // Animate and highlight the active edge
          setEdges((eds) => eds.map((e) => 
            e.id === nextEdge.id 
              ? { ...e, animated: true, style: { stroke: decision === 'YES' ? '#22c55e' : '#ef4444', strokeWidth: 4 } }
              : e
          ));
          addLog(`Routing to next node via ${decision} path...`, "info");
          await sleep(1200); // Delay to let user see the animation
        }

        // Turn off current node highlight
        setNodes((nds) => nds.map((n) => n.id === currentNodeId ? { ...n, data: { ...n.data, isActive: false } } : n));
        
        currentNodeId = nextEdge ? nextEdge.target : undefined;

      } catch (error) {
        addLog("API Error: Failed to connect to Spring Boot Backend.", "error");
        setNodes((nds) => nds.map((n) => ({ ...n, data: { ...n.data, isActive: false } })));
        break;
      }
    }
    
    addLog("Workflow execution completed.", "info");
    setIsExecuting(false);
  };

  return (
    <div className="flex flex-col w-full h-screen bg-gray-50">
      {/* Top Navigation Bar */}
      <div className="flex items-center justify-between p-4 bg-white shadow-sm border-b z-10 h-[70px]">
        <h1 className="text-xl font-bold text-gray-800">AI Decision Flow Editor</h1>
        <button 
          onClick={runWorkflow}
          disabled={isExecuting}
          className={`px-6 py-2 font-bold text-white rounded-md shadow-md transition-all ${
            isExecuting ? 'bg-gray-400 cursor-not-allowed' : 'bg-blue-600 hover:bg-blue-700 hover:shadow-lg'
          }`}
        >
          {isExecuting ? 'Running Flow...' : 'Run Workflow 🚀'}
        </button>
      </div>
      
      {/* Main Workspace Workspace */}
      <div className="flex flex-row w-full h-[calc(100vh-70px)]">
        {/* React Flow Canvas */}
        <div className="flex-grow h-full relative">
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

        {/* Execution Logs Panel */}
        <div className="w-80 h-full bg-white border-l shadow-lg flex flex-col">
          <div className="p-4 bg-gray-800 text-white font-bold text-sm uppercase tracking-wider">
            Execution Logs
          </div>
          <div className="flex-grow p-4 overflow-y-auto flex flex-col gap-3 bg-gray-50">
            {logs.length === 0 && (
              <p className="text-gray-400 text-sm italic text-center mt-4">No logs to display. Run the workflow.</p>
            )}
            {logs.map((log) => (
              <div 
                key={log.id} 
                className={`p-3 rounded-md text-sm border-l-4 shadow-sm bg-white ${
                  log.type === 'success' ? 'border-green-500' : 
                  log.type === 'error' ? 'border-red-500' : 
                  log.type === 'warning' ? 'border-yellow-400' : 'border-blue-500'
                }`}
              >
                <span className="block text-xs text-gray-400 mb-1">{log.timestamp}</span>
                <span className="font-medium text-gray-800">{log.message}</span>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default WorkflowEditor;