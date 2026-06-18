import api from "./axios";

export const signinArtist = (data) =>
    api.post("/signin/artist", data);

export const signinReviewer = (data) =>
    api.post("/signin/reviewer", data);

export const signupArtist = (data) =>
    api.post("/signup/artist", data);

export const signupReviewer = (data) =>
    api.post("/signup/reviewer", data);

export const homeProjects=(genre, minLevel, maxLevel, status) => 
    {
        return api.get("/api/artist/projects", {
            params: {
                genre, minLevel, maxLevel, status
            }
        });
    };

 export const artistProjects=(artistId)=> api.get(`/api/artist/${artistId}/projects`);

 export const postProject=(artistId, projectDto) =>
    api.post(`/api/artist/${artistId}/postProject`,projectDto);
