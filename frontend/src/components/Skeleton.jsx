import React from 'react'
import ProjectCard from './ProjectCard'

function Skeleton() {
  return (
    <div>
        <ProjectCard {...
        id=1,
        projectName="Project",
        minLevel=1,
        maxLevel=2,
        imageLink="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6ca68STDJdyI5zaAen24GBw0JjSQSJBLt26uKLyb9JYcw0LkMMqNjAvE&s=10"
        }></ProjectCard>
        
    </div>
  )
}

export default Skeleton