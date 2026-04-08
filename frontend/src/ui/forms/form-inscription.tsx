"use client";
import { useState } from "react";

export default function FormInscription() {
    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");
    const [error, setError] = useState("");

    const isMatch = password === confirm || confirm === "";

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (password !== confirm) {
            setError("Les mots de passe ne correspondent pas");
            return;
        }

        setError("");
        // Ici tu peux appeler ta fonction signup ou ton API
        console.log("Formulaire valide, envoyer les données !");
    };

    return (
        <div className="flex items-center justify-center px-6 mx-auto">
            <div className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-3xl xl:p-0">
                <div className="sm:p-8">

                    <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">

                        {/* Nom + Prénom */}
                        <div>
                            <label className="block mb-2 text-sm font-medium">Nom</label>
                            <input type="text" name="nom" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3" placeholder="Nom" required />
                        </div>

                        <div>
                            <label className="block mb-2 text-sm font-medium">Prénom</label>
                            <input type="text" name="prenom" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3" placeholder="Prénom" required />
                        </div>

                        {/* Date naissance (full width) */}
                        <div className="col-span-2">
                            <label className="block mb-2 text-sm font-medium">Date de naissance</label>
                            <input type="date" name="date" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3" required />
                        </div>

                        {/* Rue */}
                        <div className="col-span-2">
                            <label className="block mb-2 text-sm font-medium">Rue</label>
                            <input type="text" name="rue" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3" placeholder="Nom de la rue" required />
                        </div>

                        {/* Ville + CP */}
                        <div>
                            <label className="block mb-2 text-sm font-medium">Ville</label>
                            <input type="text" name="ville" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3" placeholder="Paris, Lyon..." required />
                        </div>

                        <div>
                            <label className="block mb-2 text-sm font-medium">Code Postal</label>
                            <input
                                type="text"
                                name="cp"
                                maxLength={5}
                                inputMode="numeric"
                                onChange={(e) => {
                                    e.currentTarget.value = e.currentTarget.value
                                        .replace(/\D/g, "")
                                        .slice(0, 5);
                                }}
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3"
                                placeholder="69000"
                                required
                            />
                        </div>

                        {/* Email + Login */}
                        <div>
                            <label className="block mb-2 text-sm font-medium">Email</label>
                            <input type="email" name="email" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3" placeholder="nom@email.fr" required />
                        </div>

                        <div>
                            <label className="block mb-2 text-sm font-medium">Login</label>
                            <input type="text" name="login" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3" required />
                        </div>

                        {/* Password + Confirmation */}
                        <div>
                            <label className="block mb-2 text-sm font-medium">Mot de passe</label>
                            <input
                                type="password"
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <label className="block mb-2 text-sm font-medium">Confirmer le mot de passe</label>
                            <input
                                type="password"
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3"
                                value={confirm}
                                onChange={(e) => setConfirm(e.target.value)}
                                required
                            />
                        </div>

                        {/* Erreur mot de passe */}
                        {!isMatch && (
                            <p className="col-span-2 text-red-500 text-sm">
                                Les mots de passe ne correspondent pas
                            </p>
                        )}

                        {/* Bouton bleu */}
                        <button
                            type="submit"
                            className="col-span-2 w-full bg-blue-600 hover:bg-blue-700 text-white font-medium rounded-lg px-5 py-3 transition duration-200 focus:outline-none focus:ring-4 focus:ring-blue-300"
                            disabled={!password || !confirm || !isMatch}
                        >
                            S'inscrire
                        </button>

                        {/* Login link */}
                        <p className="col-span-2 text-sm text-gray-500">
                            Déjà un compte ?
                            <a href="/login" className="font-medium text-blue-600 hover:underline ml-1">
                                Se connecter
                            </a>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    );
}