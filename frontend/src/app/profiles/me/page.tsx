"use client";

import { useEffect, useState } from "react";
import { NavBar } from "@/ui/nav-bar/nav-bar";
import { Profile } from "@/ui/profile/profile";
import { getMe, Utilisateur } from "@/services/user";
import Link from "next/link";

export default function Home() {
    const [me, setMe] = useState<Utilisateur | null>(null);

    useEffect(() => {
        getMe()
            .then(setMe)
            .catch(console.error);
    }, []);

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
                                <input type="text" value={me?.nom ?? ""} readOnly className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400" />
                            </div>
                            <div>
                                <label className="text-lg font-bold">Prénom :</label>
                                <input type="text" value={me?.prenom ?? ""} readOnly className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400" />
                            </div>
                            <div>
                                <label className="text-lg font-bold">E-mail :</label>
                                <input type="email" value={me?.email ?? ""} readOnly className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400" />
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
                                <input type="text" value={me?.rue ?? ""} readOnly className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400" />
                            </div>
                            <div>
                                <label className="text-lg font-bold">Ville :</label>
                                <input type="text" value={me?.ville ?? ""} readOnly className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400" />
                            </div>
                            <div>
                                <label className="text-lg font-bold">Code postal :</label>
                                <input type="text" value={me?.codePostal ?? ""} readOnly className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400" />
                            </div>
                            <div>
                                <label className="text-lg font-bold">Numéro de tel :</label>
                                <input type="text" value="" readOnly className="w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400" />
                            </div>
                        </div>
                    </div>

                    {/* Status */}
                    <div className="flex items-center mt-8 text-xl space-x-6">
                        <span className="text-lg font-bold">Statut :</span>
                        <label className="flex items-center gap-2">
                            <input type="radio" name="status" /> Administrateur
                        </label>
                        <label className="flex items-center gap-2">
                            <input type="radio" name="status" /> Organisateur
                        </label>
                        <label className="flex items-center gap-2">
                            <input type="radio" name="status" defaultChecked /> Participant
                        </label>
                    </div>

                    {/* Buttons */}
                    <div className="flex gap-4 mt-8">
                        <Link href="/profiles" className="flex-1">
                            <button className="w-full bg-green-400 hover:bg-green-500 text-black text-2xl py-4 rounded-lg">
                                Valider
                            </button>
                        </Link>
                        <Link href="/profiles" className="flex-1">
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
