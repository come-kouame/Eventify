"use client";

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { getParticipantByEventID, Participant } from "@/services/events";
import { NavBar } from "@/ui/nav-bar/nav-bar";

export default function Page() {
    const { id } = useParams();
    const [participants, setParticipants] = useState<Participant[]>([]);

    useEffect(() => {
        if (id) {
            getParticipantByEventID(Number(id))
                .then(setParticipants)
                .catch(console.error);
        }
    }, [id]);

    return (
        <div className="min-h-screen bg-zinc-100 font-sans">
            <NavBar />

            {/* Title */}
            <h1 className="text-5xl text-center py-10">
                Liste des participants
            </h1>

            <div className="px-10">
                {/* Table */}
                <div className="bg-zinc-300 flex flex-col gap-4 p-6 rounded-lg">
                    <table className="w-full border-collapse">
                        <thead>
                        <tr className="bg-gray-400 text-left">
                            <th className="p-3">Nom</th>
                            <th className="p-3">Prénom</th>
                        </tr>
                        </thead>
                        <tbody>
                        {participants.map((p) => (
                            <tr key={p.id} className="border-b">
                                <td className="p-3">{p.nom}</td>
                                <td className="p-3">{p.prenom}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}