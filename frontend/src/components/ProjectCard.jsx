import React from 'react'
import { useNavigate } from "react-router-dom";

function ProjectCard({
  id,
  projectName,
  minLevel,
  maxLevel,
  imageLink
}) {

  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/projects/${id}`);
  };

  return (
    <div
      onClick={handleClick}
      className="cursor-pointer bg-white rounded-xl shadow-md overflow-hidden hover:shadow-xl transition duration-300"
    >
      <img
        src={imageLink}
        alt={projectName}
        className="w-full h-48 object-cover"
      />

      <div className="p-4">
        <h2 className="text-xl font-semibold mb-2">
          {projectName}
        </h2>

        <p className="text-gray-600">
          Level: {minLevel} - {maxLevel}
        </p>
      </div>
    </div>
  );
}

export default ProjectCard