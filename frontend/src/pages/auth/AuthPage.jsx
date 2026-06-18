import { useState } from "react";

import LoginForm from "./LoginForm";
import ArtistSignupForm from "./ArtistSignupForm";
import ReviewerSignupForm from "./ReviewerSignupForm";

function AuthPage() {

    const [mode, setMode] =useState("LOGIN");

    const [role, setRole] =useState("ARTIST");

    return (
        <div className="min-h-screen flex justify-center items-center bg-gray-800">

            <div className="w-450px shadow-lg p-6 rounded bg-gray-400">

                <div className="flex gap-4 mb-5">

                    <button
                        onClick={() => setMode("LOGIN")}
                    >
                        Login
                    </button>

                    <button
                        onClick={() => setMode("SIGNUP")}
                    >
                        Signup
                    </button>

                </div>

                {
                    mode === "SIGNUP" &&
                    (
                        <select
                            className="border p-2 mb-4 w-full"
                            onChange={(e) =>
                                setRole(e.target.value)
                            }
                        >
                            <option value="ARTIST">
                                Artist
                            </option>

                            <option value="REVIEWER">
                                Reviewer
                            </option>

                        </select>
                    )
                }

                {
                    mode === "LOGIN"
                        ? <LoginForm />
                        : role === "ARTIST"
                            ? <ArtistSignupForm />
                            : <ReviewerSignupForm />
                }

            </div>

        </div>
    );
}

export default AuthPage;