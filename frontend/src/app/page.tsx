"use client";

import {NavBar} from "@/ui/nav-bar/nav-bar"
import {Calendar} from "@/ui/calendar/calendar";
import {BlocEvenements} from "@/ui/event/bloc-evenements"
import {Hello} from "@/ui/message/hello";
import { useEffect, useState } from "react";
import { getMe, Utilisateur } from "@/services/user";
import { getAllValidEvents, Event } from "@/services/events";

export default function Home() {
    const [me, setMe] = useState<Utilisateur | null>(null);
    const [events, setEvents] = useState<Event[]>([]);

    const [search, setSearch] = useState("");

    const filteredEvents = events.filter((event) =>
        event.titre.toLowerCase().includes(search.toLowerCase())
    );


    useEffect(() => {
        getMe()
            .then(setMe)
            .catch((err) => console.error("getMe error:", err));

        getAllValidEvents()
            .then(setEvents)
            .catch((err) => console.error("getAllValidEvents error:", err));
    }, []);
    return (
        <div className=" justify-center bg-zinc-50 font-sans">
            <NavBar/>
            <h1 className="font-medium text-black text-6xl text-center w-full py-10">Nos prochains évènements</h1>
            <main className="flex min-h-screen w-full px-16 sm:items-start">
                {/* Left calendar */}
                <div className="flex flex-col gap-4 mx-4">
                    <input
                        type="text"
                        placeholder="Rechercher un évènement..."
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                        className="w-full rounded-lg border border-gray-300 px-4 py-2 text-black shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                    <Calendar
                        date={new Date()}
                        eventsDates={filteredEvents.map((e) => new Date(e.date_debut))}
                    />
                    <Calendar
                        date={new Date(new Date().getFullYear(), new Date().getMonth() + 1, 1)}
                        eventsDates={filteredEvents.map((e) => new Date(e.date_debut))}
                    />
                </div>

                {/* Event */}
                <BlocEvenements data={filteredEvents}/>
            </main>
            <Hello/>
            {me && (
                <div className="text-center mt-6">
                    <p className="text-lg font-semibold">Utilisateur connecté</p>
                    <p>{me.prenom} {me.nom}</p>
                    <p className="text-sm text-gray-500">{me.email}</p>
                    <p className="text-sm text-gray-500">{me.ville}</p>
                </div>
            )}
        </div>
    );
}
