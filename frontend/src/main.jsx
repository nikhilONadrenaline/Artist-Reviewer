import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import ArtistLayout from './layouts/ArtistLayout';
import "./index.css";
import AuthPage from "./pages/auth/AuthPage";
import Home from './pages/artist/Home';
import Projects from './pages/artist/Projects';
import AddProject from './pages/artist/AddProject';
import DashBoard from './pages/artist/DashBoard';
import UserContextProvider from "./context/UserContextProvider";
import ProjectDetail from './pages/artist/ProjectDetail'
import { selectProject } from "./api/artistAPI";



const router = createBrowserRouter([
    {
        path: "/",
        element: <AuthPage />
    },
    {
        path:"/",
        element:<ArtistLayout/>,
        children: [
            {
                path: "home",
                element: <Home/>
            },
            {
                path: "projects",
                element: <Projects/>,
            },
            {
                path: "add-project",
                element: <AddProject/>
            },
            {
                path:"dashboard",
                element:<DashBoard/>
            },
            {
                path:"projects/:id",
                element:<ProjectDetail/>,
                loader: async ({ params }) => {
                    const response=await selectProject(params.id);
                    return response.data;
                }
            }
        ]
    }
    
]);

createRoot(document.getElementById("root")).render(
    <UserContextProvider>
    <StrictMode>
        <RouterProvider router={router} />
    </StrictMode>
    </UserContextProvider>
);