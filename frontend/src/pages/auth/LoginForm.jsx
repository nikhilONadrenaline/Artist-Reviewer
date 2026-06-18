import { useContext, useState } from "react";
import { signinArtist, signinReviewer } from "../../api/authApi";
import UserContext from "../../context/UserContext";
import { useNavigate } from 'react-router-dom'

function LoginForm() {

    const {setUser}=useContext(UserContext)

    const [formData, setFormData] = useState({
        emailId: "",
        password: "",
        role: "ARTIST"
    });

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const navigate=useNavigate()

    const handleSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            emailId: formData.emailId,
            password: formData.password
        };

        try {

            const response =
                formData.role === "ARTIST"
                    ? await signinArtist(payload)
                    : await signinReviewer(payload);
            
            setUser(response.data)
            localStorage.setItem("token",response.data.token)
            localStorage.setItem("user",JSON.stringify(response.data))
            console.log(response.data);
            navigate("/home")

        } catch (err) {
            console.log(err, " Maybe Invalid Credentials");
        }
    };

    return (
        <form
            onSubmit={handleSubmit}
            className="flex flex-col gap-4"
        >
            <input
                type="email"
                name="emailId"
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

            <select
                name="role"
                onChange={handleChange}
                className="border p-2 rounded"
            >
                <option value="ARTIST">Artist</option>
                <option value="REVIEWER">Reviewer</option>
            </select>

            <button
                className="bg-blue-600 text-white p-2 rounded"
            >
                Login
            </button>
        </form>
    );
}

export default LoginForm;