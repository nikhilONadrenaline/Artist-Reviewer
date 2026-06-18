import { useState } from "react";
import { signupArtist } from "../../api/authApi";

function ArtistSignupForm() {

    const [artist, setArtist] = useState({
        name: "",
        genre: "",
        upiId: "",
        email: "",
        password: "",
        role: "ARTIST"
    });

    const handleChange = (e) => {
        setArtist({
            ...artist,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        const payload = {
            ...artist,
            genre: artist.genre
                .split(",")
                .map(item => item.trim())
        };

        try {
            const response =
                await signupArtist(payload);

            console.log(response.data);

        } catch (err) {
            console.log(err);
        }
    };

    return (
        <form
            onSubmit={handleSubmit}
            className="flex flex-col gap-4"
        >

            <input
                name="name"
                placeholder="Name"
                onChange={handleChange}
                className="border p-2 rounded"
            />

            <input
                name="genre"
                placeholder="Genres (comma separated)"
                onChange={handleChange}
                className="border p-2 rounded"
            />

            <input
                name="upiId"
                placeholder="UPI ID"
                onChange={handleChange}
                className="border p-2 rounded"
            />

            <input
                type="email"
                name="email"
                placeholder="Email"
                onChange={handleChange}
                className="border p-2 rounded"
            />

            <input
                type="password"
                name="password"
                placeholder="Password"
                onChange={handleChange}
                className="border p-2 rounded"
            />

            <button
                className="bg-green-600 text-white p-2 rounded"
            >
                Signup Artist
            </button>

        </form>
    );
}

export default ArtistSignupForm;