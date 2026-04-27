"use client";

import { useState } from "react";

type FormErrors = {
    nom?: string;
    prenom?: string;
    date?: string;
    rue?: string;
    ville?: string;
    cp?: string;
    email?: string;
    password?: string;
    confirm?: string;
    general?: string;
};

export default function FormInscription() {
    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");
    const [errors, setErrors] = useState<FormErrors>({});

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const formData = new FormData(e.currentTarget);

        const nom = (formData.get("nom") as string).trim();
        const prenom = (formData.get("prenom") as string).trim();
        const date = formData.get("date") as string;
        const rue = (formData.get("rue") as string).trim();
        const ville = (formData.get("ville") as string).trim();
        const cp = (formData.get("cp") as string).trim();
        const email = (formData.get("email") as string).trim();

        const newErrors: FormErrors = {};
        const regexNom = /^[A-Za-zÀ-ÿ\s-]+$/;
        const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!nom) newErrors.nom = "Le nom est requis";
        else if (!regexNom.test(nom)) newErrors.nom = "Le nom doit contenir uniquement des lettres";

        if (!prenom) newErrors.prenom = "Le prénom est requis";
        else if (!regexNom.test(prenom)) newErrors.prenom = "Le prénom doit contenir uniquement des lettres";

        if (!date) newErrors.date = "La date de naissance est requise";
        if (!rue) newErrors.rue = "La rue est requise";
        if (!ville) newErrors.ville = "La ville est requise";

        if (!cp) newErrors.cp = "Le code postal est requis";
        else if (cp.length !== 5) newErrors.cp = "Le code postal doit contenir 5 chiffres";

        if (!email) newErrors.email = "L'email est requis";
        else if (!regexEmail.test(email)) newErrors.email = "Email invalide";

        if (!password) newErrors.password = "Le mot de passe est requis";
        else if (password.length < 6) newErrors.password = "Le mot de passe doit contenir au moins 6 caractères";

        if (!confirm) newErrors.confirm = "La confirmation est requise";
        else if (password !== confirm) newErrors.confirm = "Les mots de passe ne correspondent pas";

        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors);
            return;
        }

        setErrors({});

        const body = {
            nom,
            prenom,
            dateNaissance: date,
            rue,
            ville,
            codePostal: cp,
            email,
            mdp: password,
        };

        try {
            const res = await fetch("http://localhost:8080/utilisateurs", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(body),
            });

            if (!res.ok) {
                setErrors({ general: "Erreur lors de l'inscription" });
                return;
            }

            window.location.href = "/login";
        } catch {
            setErrors({ general: "Erreur lors de l'inscription" });
        }
    };

    return (
        <div className="flex items-center justify-center px-6 mx-auto">
          <div className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-3xl xl:p-0">
                <div className="sm:p-8">
                    <form onSubmit={handleSubmit} noValidate className="grid grid-cols-2 gap-4">
                        <div>
                            <label className="block mb-2 text-sm font-medium">Nom</label>
                            <input type="text" name="nom" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg block w-full p-3" placeholder="Nom" />
                            <p className="text-red-500 text-xs mt-1 h-2">{errors.nom || ""}</p>
                        </div>

                        <div>
                            <label className="block mb-2 text-sm font-medium">Prénom</label>
                            <input type="text" name="prenom" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg block w-full p-3" placeholder="Prénom" />
                            <p className="text-red-500 text-xs mt-1 h-2">{errors.prenom || ""}</p>
                        </div>

                        <div className="col-span-2">
                            <label className="block mb-2 text-sm font-medium">Date de naissance</label>
                            <input type="date" name="date" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg block w-full p-3" />
                            <p className="text-red-500 text-xs mt-1 h-2">{errors.date || ""}</p>
                        </div>

                        <div className="col-span-2">
                            <label className="block mb-2 text-sm font-medium">Rue</label>
                            <input type="text" name="rue" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg block w-full p-3" placeholder="Nom de la rue" />
                            <p className="text-red-500 text-xs mt-1 h-2">{errors.rue || ""}</p>
                        </div>

                        <div>
                            <label className="block mb-2 text-sm font-medium">Ville</label>
                            <input type="text" name="ville" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg block w-full p-3" placeholder="Paris, Lyon..." />
                            <p className="text-red-500 text-xs mt-1 h-2">{errors.ville || ""}</p>
                        </div>

                        <div>
                            <label className="block mb-2 text-sm font-medium">Code Postal</label>
                            <input
                                type="text"
                                name="cp"
                                maxLength={5}
                                inputMode="numeric"
                                onChange={(e) => {
                                    e.currentTarget.value = e.currentTarget.value.replace(/\D/g, "").slice(0, 5);
                                }}
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg block w-full p-3"
                                placeholder="69000"
                            />
                            <p className="text-red-500 text-xs mt-1 h-2">{errors.cp || ""}</p>
                        </div>

                        <div className="col-span-2">
                            <label className="block mb-2 text-sm font-medium">Email</label>
                            <input type="email" name="email" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg block w-full p-3" placeholder="nom@email.fr" />
                            <p className="text-red-500 text-xs mt-1 h-2">{errors.email || ""}</p>
                        </div>

                        <div>
                            <label className="block mb-2 text-sm font-medium">Mot de passe</label>
                            <input
                                type="password"
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg block w-full p-3"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                            <p className="text-red-500 text-xs mt-1 h-2">{errors.password || ""}</p>
                        </div>

                        <div>
                            <label className="block mb-2 text-sm font-medium">Confirmer le mot de passe</label>
                            <input
                                type="password"
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg block w-full p-3"
                                value={confirm}
                                onChange={(e) => setConfirm(e.target.value)}
                            />
                            <p className="text-red-500 text-xs mt-1 h-2">{errors.confirm || ""}</p>
                        </div>

                        <p className="col-span-2 text-red-500 text-xs h-2">{errors.general || ""}</p>

                        <button
                            type="submit"
                            className="col-span-2 w-full bg-blue-600 hover:bg-blue-700 text-white font-medium rounded-lg px-5 py-3 transition duration-200 focus:outline-none focus:ring-4 focus:ring-blue-300"
                        >
                            S'inscrire
                        </button>

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