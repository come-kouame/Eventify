"use client";

import {NavBar} from "@/ui/nav-bar/nav-bar"
import {Calendar} from "@/ui/calendar/calendar";
import {BlocEvenements} from "@/ui/event/bloc-evenements"
import {Hello} from "@/ui/message/hello";
import { useEffect, useState } from "react";
import { getMe, Utilisateur } from "@/services/user";

export default function Home() {
    const [me, setMe] = useState<Utilisateur | null>(null);

    useEffect(() => {
        getMe()
            .then(setMe)
            .catch((err) => console.error("getMe error:", err));
    }, []);
    return (
        <div className=" justify-center bg-zinc-50 font-sans">
            <NavBar/>
            <h1 className="font-medium text-black text-6xl text-center w-full py-10">Nos prochains évènements</h1>
            <main className="flex min-h-screen w-full px-16 sm:items-start">
                {/* Left calendar */}
                <div className="flex flex-col gap-4 mx-4">
                    <Calendar
                        date={new Date()}
                    />
                    <Calendar
                        date={new Date(new Date().getFullYear(), new Date().getMonth() + 1, 1)}
                    />
                </div>

                {/* Event */}
                <BlocEvenements/>
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
