"use client";
import { useState } from "react";
import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected";
import {NavBar} from "@/ui/nav-bar/nav-bar";

import { LigneProfile } from "@/ui/profile/ligne-profile";

export default function Home() {
    const [filters, setFilters] = useState<string[]>([]);

    const toggleFilter = (filter: string) => {
        setFilters((prev) =>
            prev.includes(filter)
                ? prev.filter((f) => f !== filter)
                : [...prev, filter]
        );
    };

    return (
        <div className="min-h-screen bg-zinc-100 font-sans">
            <NavBar/>

            {/* Title */}
            <h1 className="text-5xl text-center py-10">
                Gestion des comptes
            </h1>

            <div className="flex px-10 gap-6">
                {/* Sidebar */}
                <div className="w-1/4 flex flex-col gap-6 rounded-lg">
                    <div className="bg-zinc-300 p-6 flex flex-col gap-4 rounded-lg">
                        <h2 className="text-3xl text-center">Filtres</h2>
                        <div className="text-lg flex flex-col gap-3">
                            <label className="flex items-center gap-2">
                                <input
                                    type="checkbox"
                                    checked={filters.includes("Administrateur")}
                                    onChange={() => toggleFilter("Administrateur")}
                                />
                                Administrateur
                            </label>
                            <label className="flex items-center gap-2">
                                <input
                                    type="checkbox"
                                    checked={filters.includes("Organisateur")}
                                    onChange={() => toggleFilter("Organisateur")}
                                />
                                Organisateur
                            </label>
                            <label className="flex items-center gap-2">
                                <input
                                    type="checkbox"
                                    checked={filters.includes("Utilisateur")}
                                    onChange={() => toggleFilter("Utilisateur")}
                                />
                                Utilisateur
                            </label>
                        </div>
                    </div>
                </div>

                {/* Table */}
                <div className="flex-1 bg-zinc-300 flex flex-col gap-4 p-6 rounded-lg">
                    <table className="w-full border-collapse">
                        <thead>
                            <tr className="bg-gray-400 text-left">
                                <th className="p-2">Nom</th>
                                <th className="p-2">Prénom</th>
                                <th className="p-2">E-mail</th>
                                <th className="p-2">Statut</th>
                                <th className="p-2">Modifier</th>
                            </tr>
                        </thead>
                        <tbody>
                            <LigneProfile filters={filters} />
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}
