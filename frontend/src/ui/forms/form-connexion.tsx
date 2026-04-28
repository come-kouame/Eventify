"use client";

import { useState } from "react";
export function FormConnexion() {
    const [email, setEmail] = useState("");
    const [mdp, setMdp] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e: any) => {
    e.preventDefault();

        try {
            const res = await fetch("https://192.168.75.28/api/eventify/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email: email,
                    mdp: mdp,
                }),
            });

            console.log("STATUS:", res.status);
            console.log("OK:", res.ok);

            const text = await res.text();
            console.log("back repond:", text);

            if (!res.ok) {
                throw new Error("Identifiants invalides");
            }

            const data = JSON.parse(text);
            console.log("Connexion réussie :", data);

            window.location.href = "/";
        } catch (err) {
            setError("Email ou mot de passe incorrect");
        }
    };

    return (
        <div className="flex items-center justify-center px-6 mx-auto w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-2xl xl:p-0">
            <div className="p-6 space-y-6 sm:p-8 w-full">
                <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit}>
                    <div className="flex gap-4">
                        <div className="flex-1">
                            <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900">
                                E-Mail
                            </label>
                            <input
                                type="email"
                                name="email"
                                id="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3"
                                placeholder="nom@email.fr"
                                required
                            />
                        </div>

                        <div className="flex-1">
                            <label htmlFor="mdp" className="block mb-2 text-sm font-medium text-gray-900">
                                Mot de Passe
                            </label>
                            <input
                                type="password"
                                name="mdp"
                                id="mdp"
                                value={mdp}
                                onChange={(e) => setMdp(e.target.value)}
                                placeholder="••••••••"
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3"
                                required
                            />
                        </div>
                    </div>

                    {error && <p className="text-sm text-red-600">{error}</p>}

                    <button
                        type="submit"
                        className="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium rounded-lg px-5 py-3 text-center transition duration-200 focus:outline-none focus:ring-4 focus:ring-blue-300"
                    >
                        Connexion
                    </button>

                    <p className="text-sm font-light text-gray-500">
                        Pas encore de compte ?
                        <a href="/signup" className="font-medium text-blue-600 hover:underline ml-1">
                            S'inscrire
                        </a>
                    </p>
                </form>
            </div>
        </div>
    );
}