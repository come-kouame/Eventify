"use client";

import {Calendar} from '@/ui/calendar/calendar';
import { use, useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import {NavBar} from "@/ui/nav-bar/nav-bar";
import { getEventByID, valideEvent, deleteEvent } from "@/services/events";

export default function Home({ params }: { params: Promise<{ id: string }> }) {
    const { id } = use(params);
    const router = useRouter();
    const eventDate = new Date(2026, 4, 29);

    const [name, setName] = useState("");
    const [date, setDate] = useState(eventDate);
    const [location, setLocation] = useState("");
    const [price, setPrice] = useState("");
    const [seats, setSeats] = useState("");
    const [startTime, setStartTime] = useState("");
    const [endTime, setEndTime] = useState("");
    const [description, setDescription] = useState("");
    const [posterPreview, setPosterPreview] = useState("");
    const [calendarMonth, setCalendarMonth] = useState(eventDate);

    useEffect(() => {
        const loadEvent = async () => {
            try {
                const event = await getEventByID(Number(id));
                setName(event.titre);
                setDescription(event.description);
                setLocation(event.lieu);
                setPrice(String(event.prix));
                setSeats(String(event.nb_places));
                setPosterPreview(event.poster);

                const start = new Date(event.date_debut);
                const end = new Date(event.date_fin);

                setDate(start);
                setCalendarMonth(start);
                setStartTime(start.toTimeString().slice(0, 5));
                setEndTime(end.toTimeString().slice(0, 5));
            } catch (err) {
                console.error("Load event error:", err);
            }
        };

        loadEvent();
    }, [id]);

    // Helper function for date/time conversion
    const buildDateTime = (baseDate: Date, time: string) => {
        if (!time || !time.includes(":")) {
            return new Date(baseDate);
        }

        const [hours, minutes] = time.split(":").map(Number);

        const newDate = new Date(baseDate);
        newDate.setHours(hours || 0, minutes || 0, 0, 0);

        return newDate;
    };

    const handleValidateEvent = async () => {
        const dateDebut = buildDateTime(date, startTime);
        const dateFin = buildDateTime(date, endTime);

        const eventPayload = {
            titre: name,
            description,
            date_debut: dateDebut.toLocaleString("sv-SE", { timeZone: "Europe/Paris" }).replace(" ", "T"),
            date_fin: dateFin.toLocaleString("sv-SE", { timeZone: "Europe/Paris" }).replace(" ", "T"),
            lieu: location,
            adresse: "",
            prix: Number(price),
            nb_places: Number(seats),
            poster: "",
            nb_places_vendues: 0,
            valide: true,
            orga_id: 1,
            admin_id: 1,
        };
        try {
            console.log(JSON.stringify(eventPayload));
            await valideEvent(Number(id), eventPayload);
            router.push("/event/manage");
        } catch (err) {
            console.error("Validate event error:", err);
        }
    };

    const handleDeleteEvent = async () => {
        try {
            await deleteEvent(Number(id));
            router.push("/event/manage");
        } catch (err) {
            console.error("Delete event error:", err);
        }
    };

    return (
        <div className="justify-center bg-zinc-50 font-sans pb-4">
            <NavBar/>

            <input
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="font-medium my-5 mx-10 text-black text-6xl text-center w-[calc(100%-5rem)] py-5 bg-white border border-zinc-300 rounded-lg outline-none focus:ring-2 focus:ring-indigo-400"
                placeholder="Nom de l’évènement"
            />
            <main className="flex w-full gap-8 px-16">
                {/* Left info panel */}
                <div className="w-1/4 bg-zinc-200 p-6 flex flex-col items-center justify-start text-center rounded-lg gap-6">
                    {/* Calendar */}
                    <Calendar
                        date={calendarMonth}
                        selectedDate={date}
                        onSelectDate={(d) => setDate(d)}
                        onMonthChange={(d) => setCalendarMonth(d)}
                        className="w-full"
                    />

                    {/* Informations importantes */}
                    <div className="flex flex-col gap-6 w-full text-center">
                        <div>
                            <p className="text-xl font-bold text-center">Lieu</p>
                            <input
                                value={location}
                                onChange={(e) => setLocation(e.target.value)}
                                className="text-center w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                placeholder="Ex: Palais des Congrès, Lyon"
                            />
                        </div>
                        <div>
                            <p className="text-xl font-bold text-center">Prix</p>
                            <input
                                value={price}
                                onChange={(e) => setPrice(e.target.value)}
                                className="text-center w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                placeholder="Ex: 20€"
                            />
                        </div>
                        <div>
                            <p className="text-xl font-bold text-center">Nombre de places disponibles</p>
                            <input
                                value={seats}
                                onChange={(e) => setSeats(e.target.value)}
                                className="text-center w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                placeholder="Ex: 100"
                            />
                        </div>
                        <div>
                            <p className="text-xl font-bold text-center">Heure</p>
                            <div className="flex gap-2">
                                <input
                                    value={startTime}
                                    onChange={(e) => setStartTime(e.target.value)}
                                    className="w-1/2 text-center bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                    placeholder="Ex: 18:00"
                                />
                                -
                                <input
                                    value={endTime}
                                    onChange={(e) => setEndTime(e.target.value)}
                                    className="w-1/2 text-center bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                    placeholder="Ex: 22:00"
                                />
                            </div>
                        </div>
                    </div>
                </div>

                {/* Right content */}
                <div className="flex-1 flex flex-col gap-6">
                    {/* Description with poster */}
                    <div className="bg-zinc-200 flex-1 flex rounded-lg p-6 gap-6">
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            className="text-xl flex-1 bg-white border border-zinc-300 rounded-lg p-4 outline-none resize-none focus:ring-2 focus:ring-indigo-400"
                            placeholder="Décris ton événement..."
                        />
                    </div>

                    {/* Buttons */}
                    <div className="flex gap-4">
                        <div className="flex-1">
                            <button
                                onClick={handleValidateEvent}
                                className="w-full bg-green-400 hover:bg-green-500 text-black text-2xl py-4 rounded-lg"
                            >
                                Valider
                            </button>
                        </div>
                        <div className="flex-1">
                            <button
                                onClick={handleDeleteEvent}
                                className="w-full bg-red-400 hover:bg-red-500 text-black text-2xl py-4 rounded-lg"
                            >
                                Annuler
                            </button>
                        </div>
                        <Link href="/event/manage" className="flex-1">
                            <button className="w-full bg-gray-400 hover:bg-gray-500 text-black text-2xl py-4 rounded-lg">
                                Retour
                            </button>
                        </Link>
                    </div>
                </div>
            </main>
        </div>
    );
}
