"use client";

import { Calendar } from "@/ui/calendar/calendar";
import { NavBar } from "@/ui/nav-bar/nav-bar";
import { getEventByID, Event } from "@/services/events";
import { useEffect, useState, use } from "react";
export default function EventPage({params,}: {
    params: Promise<{ id: string }>;
}) {
    const { id } = use(params);
    const [event, setEvent] = useState<Event | null>(null);
    useEffect(() => {
        getEventByID(Number(id))
            .then(setEvent)
            .catch(console.error);
    }, [id]);

    if (!event) return <p>Chargement...</p>;
    const eventDate = new Date(event.date_debut);

    return (
        <div className="justify-center bg-zinc-50 font-sans">
            <NavBar />

            <h1 className="font-medium text-black text-6xl text-center w-full py-10">
                {event.titre}
            </h1>

            <main className="flex w-full gap-8 px-16">
                <div className="w-1/4 bg-zinc-200 p-6 flex flex-col items-center justify-start text-center rounded-lg gap-6">
                    <Calendar
                        date={eventDate}
                        startDate={null}
                        endDate={null}
                        onSelectDate={() => {}}
                        className="w-full"
                    />

                    <div className="flex flex-col gap-6 w-full text-center">
                        <div>
                            <p className="text-xl font-bold">Lieu</p>
                            <p>{event.lieu}</p>
                        </div>
                        <div>
                            <p className="text-xl font-bold">Prix</p>
                            <p>{event.prix <= 0 ? "Gratuit" : `${event.prix}€`}</p>
                        </div>
                        <div>
                            <p className="text-xl font-bold">Places restantes</p>
                            <p>{event.nb_places - event.nb_places_vendues}</p>
                        </div>
                    </div>
                </div>

                <div className="flex-1 flex flex-col gap-6">
                    <div className="bg-zinc-200 flex-1 flex rounded-lg p-6">
                        {event.poster ? (
                            <img
                                src={event.poster}
                                alt="Poster"
                                className="w-1/4 h-auto rounded-lg mr-6 bg-zinc-300"
                            />
                        ) : (
                            <div className="w-1/4 rounded-lg mr-6 bg-zinc-300 flex items-center justify-center">
                                Poster
                            </div>
                        )}

                        <p className="text-4xl text-center flex-1 flex items-center justify-center">
                            {event.description}
                        </p>
                    </div>

                    <button className="bg-indigo-300 text-black text-3xl py-6 rounded-lg hover:bg-indigo-400">
                        Je m’inscris
                    </button>
                </div>
            </main>
        </div>
    );
}