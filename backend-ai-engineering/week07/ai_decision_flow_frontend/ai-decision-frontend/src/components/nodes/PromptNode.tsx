import { Handle, Position, type NodeProps } from 'reactflow';

const PromptNode = ({ id, data }: NodeProps) => {
  return (
    <div className="bg-white border-2 border-blue-500 rounded-lg p-4 shadow-lg w-72 relative">
      {/* Target Handle: To receive connection from previous nodes */}
      <Handle 
        type="target" 
        position={Position.Top} 
        className="w-3 h-3 bg-blue-500" 
      />

      <div className="flex flex-col gap-2">
        <label className="text-sm font-bold text-gray-700">
          {data.label || 'AI Decision Node'}
        </label>
        <textarea
          className="w-full p-2 text-sm border rounded-md resize-none focus:outline-none focus:ring-2 focus:ring-blue-500"
          rows={3}
          placeholder="Enter prompt (e.g., Is this a support request?)"
          value={data.prompt}
          onChange={(e) => data.onChange(id, e.target.value)}
        />
      </div>

      {/* YES Handle (Source) */}
      <Handle
        type="source"
        position={Position.Bottom}
        id="yes"
        style={{ left: '30%', background: '#22c55e' }}
        className="w-4 h-4 border-2 border-white"
      />
      <div className="absolute -bottom-6 left-[22%] text-xs font-bold text-green-600">
        YES
      </div>

      {/* NO Handle (Source) */}
      <Handle
        type="source"
        position={Position.Bottom}
        id="no"
        style={{ left: '70%', background: '#ef4444' }}
        className="w-4 h-4 border-2 border-white"
      />
      <div className="absolute -bottom-6 left-[65%] text-xs font-bold text-red-600">
        NO
      </div>
    </div>
  );
};

export default PromptNode;