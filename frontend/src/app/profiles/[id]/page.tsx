"use client";
import { useEffect, useState } from "react";
import { NavBar } from "@/ui/nav-bar/nav-bar";
import { getStatut, getUserByID, modifyUser, setAdmin, setOrga, setUser, Utilisateur } from "@/services/user";
import Link from "next/link";
import { useParams } from "next/navigation";

export default function Home() {
    const params = useParams();
    const id = params.id;
    const [me, setMe] = useState<Utilisateur | null>(null);
    const [formData, setFormData] = useState<Omit<Utilisateur, "id" | "id_admin" | "id_orga"> | null>(null);
    const [status, setStatus] = useState("Utilisateur");

    useEffect(() => {
        getUserByID(Number(id))
            .then((data) => {
                setMe(data);
                setFormData({
                    nom: data.nom,
                    prenom: data.prenom,
                    dateNaissance: data.dateNaissance,
                    email: data.email,
                    ville: data.ville,
                    rue: data.rue,
                    codePostal: data.codePostal,
                });
                setStatus(getStatut(data));
            })
            .catch(console.error);
    }, []);

    const handleChange = (field: keyof NonNullable<typeof formData>, value: string) => {
        setFormData((prev) => prev ? { ...prev, [field]: value } : prev);
    };

    const handleSubmit = async () => {
        if (!me || !formData) return;
        try {
            const updated = await modifyUser(me.id, formData);
            setMe(updated);

            if (status === "Administrateur") {
                await setAdmin(me.id);
            } else if (status === "Organisateur") {
                await setOrga(me.id);
            } else {
                await setUser(me.id);
            }
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className="min-h-screen font-sans">
            <NavBar />

            <h1 className="font-medium text-black text-6xl text-center w-full py-10">
                Profil de {me ? `${me.prenom} ${me.nom}` : "..."}
            </h1>

            <div className="px-6">
                <div className="bg-gray-200 p-6 rounded-lg">
                    <div className="grid grid-cols-2 gap-8 text-xl">
                        {/* Left column */}
                        <div className="space-y-4">
                            <div>
                                <label className="text-lg font-bold">Nom :</label>
                                <input
                                    type="text"
                                    value={formData?.nom ?? ""}
                                    onChange={(e) => handleChange("nom", e.target.value)}
                                    className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                />
                            </div>
                            <div>
                                <label className="text-lg font-bold">Prénom :</label>
                                <input
                                    type="text"
                                    value={formData?.prenom ?? ""}
                                    onChange={(e) => handleChange("prenom", e.target.value)}
                                    className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                />
                            </div>
                            <div>
                                <label className="text-lg font-bold">E-mail :</label>
                                <input
                                    type="email"
                                    value={formData?.email ?? ""}
                                    onChange={(e) => handleChange("email", e.target.value)}
                                    className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                />
                            </div>
                            <div>
                                <label className="text-lg font-bold">Mot de passe :</label>
                                <input type="password" value="" readOnly className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400" />
                            </div>
                        </div>

                        {/* Right column */}
                        <div className="space-y-4">
                            <div>
                                <label className="text-lg font-bold">Adresse :</label>
                                <input
                                    type="text"
                                    value={formData?.rue ?? ""}
                                    onChange={(e) => handleChange("rue", e.target.value)}
                                    className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                />
                            </div>
                            <div>
                                <label className="text-lg font-bold">Ville :</label>
                                <input
                                    type="text"
                                    value={formData?.ville ?? ""}
                                    onChange={(e) => handleChange("ville", e.target.value)}
                                    className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                />
                            </div>
                            <div>
                                <label className="text-lg font-bold">Code postal :</label>
                                <input
                                    type="text"
                                    value={formData?.codePostal ?? ""}
                                    onChange={(e) => handleChange("codePostal", e.target.value)}
                                    className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                />
                            </div>
                            <div>
                                <label className="text-lg font-bold">Date de naissance :</label>
                                <input
                                    type="date"
                                    defaultValue={me?.dateNaissance}
                                    className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                />
                            </div>
                        </div>
                    </div>

                    {/* Status */}
                    <div className="flex items-center mt-8 text-xl space-x-6">
                        <span className="text-lg font-bold">Statut :</span>
                        <label className="flex items-center gap-2">
                            <input type="radio" name="status" checked={status === "Administrateur"} onChange={() => setStatus("Administrateur")} /> Administrateur
                        </label>
                        <label className="flex items-center gap-2">
                            <input type="radio" name="status" checked={status === "Organisateur"} onChange={() => setStatus("Organisateur")} /> Organisateur
                        </label>
                        <label className="flex items-center gap-2">
                            <input type="radio" name="status" checked={status === "Utilisateur"} onChange={() => setStatus("Utilisateur")} /> Utilisateur
                        </label>
                    </div>

                    {/* Buttons */}
                    <div className="flex gap-4 mt-8">
                        <button
                            onClick={handleSubmit}
                            className="flex-1 w-full bg-green-400 hover:bg-green-500 text-black text-2xl py-4 rounded-lg"
                        >
                            Valider
                        </button>
                        <Link href="/" className="flex-1">
                            <button className="w-full bg-red-400 hover:bg-red-500 text-black text-2xl py-4 rounded-lg">
                                Annuler
                            </button>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
}
