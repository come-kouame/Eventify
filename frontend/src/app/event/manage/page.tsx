"use client";
import { useState } from "react";
import {NavBar} from "@/ui/nav-bar/nav-bar";
import {BlocModify} from "@/ui/event/bloc-modify"

export default function Home() {
    const [filters, setFilters] = useState({
        enCours: false,
        aValider: false,
        passe: false,
    });

    return (
        <div className="min-h-screen bg-zinc-100 font-sans">
            <NavBar/>

            {/* Title */}
            <h1 className="text-5xl text-center py-10">
                Les Événements organisés
            </h1>

            <div className="flex px-10 gap-6">

                {/* Left panel */}
                <div className="w-1/4 flex flex-col gap-6 rounded-lg">

                    {/* Create event button */}
                    <a href="/event/create" className="py-4 text-xl rounded-lg bg-indigo-300 text-center">
                        Créer un event
                    </a>

                    {/* Filters */}
                    <div className="bg-zinc-300 p-6 flex flex-col gap-4 rounded-lg">
                        <h2 className="text-3xl text-center">Filtres</h2>
                        <div className="text-lg flex flex-col gap-3">
                            <label className="flex items-center gap-2">
                                <input
                                    type="checkbox"
                                    checked={filters.enCours}
                                    onChange={() => setFilters({...filters, enCours: !filters.enCours})}
                                />
                                En cours
                            </label>
                            <label className="flex items-center gap-2">
                                <input
                                    type="checkbox"
                                    checked={filters.aValider}
                                    onChange={() => setFilters({...filters, aValider: !filters.aValider})}
                                />
                                À valider
                            </label>
                            <label className="flex items-center gap-2">
                                <input
                                    type="checkbox"
                                    checked={filters.passe}
                                    onChange={() => setFilters({...filters, passe: !filters.passe})}
                                />
                                Passé
                            </label>
                        </div>
                    </div>
                </div>

                {/* Right panel */}
                <div className="flex-1 bg-zinc-300 flex flex-col gap-4 p-6 rounded-lg">
                    <BlocModify filters={filters} />
                </div>

            </div>
        </div>
    );
}
