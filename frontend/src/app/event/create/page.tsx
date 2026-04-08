"use client";

import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected";
import {Calendar} from '@/ui/calendar/calendar';
import { useState } from "react";
import Link from "next/link";

export default function Home() {
    const eventDate = new Date(2026, 3, 7);

    const [name, setName] = useState("Nom de l’évènement");
    const [date, setDate] = useState(eventDate);
    const [location, setLocation] = useState("Palais des Congrès, Lyon");
    const [price, setPrice] = useState("20€");
    const [seats, setSeats] = useState("100");
    const [startTime, setStartTime] = useState("18:00");
    const [endTime, setEndTime] = useState("22:00");
    const [description, setDescription] = useState("Description de l’évènements");
    const [posterFile, setPosterFile] = useState<File | null>(null);
    const [posterPreview, setPosterPreview] = useState("/poster-example.jpg");

    return (
        <div className="justify-center bg-zinc-50 font-sans pb-4">
            <NavBarNotConnected />

            <input
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="font-medium my-5 mx-10 text-black text-6xl text-center w-[calc(100%-5rem)] py-5 bg-white border border-zinc-300 rounded-lg outline-none focus:ring-2 focus:ring-indigo-400"
            />
            <main className="flex w-full gap-8 px-16">
                {/* Left info panel */}
                <div className="w-1/4 bg-zinc-200 p-6 flex flex-col items-center justify-start text-center rounded-lg gap-6">
                    {/* Calendar */}
                    <Calendar
                        date={date}
                        startDate={null}
                        endDate={null}
                        onSelectDate={(d) => setDate(d)}
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
                            />
                        </div>
                        <div>
                            <p className="text-xl font-bold text-center">Prix</p>
                            <input
                                value={price}
                                onChange={(e) => setPrice(e.target.value)}
                                className="text-center w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                            />
                        </div>
                        <div>
                            <p className="text-xl font-bold text-center">Nombre de places disponibles</p>
                            <input
                                value={seats}
                                onChange={(e) => setSeats(e.target.value)}
                                className="text-center w-full bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                            />
                        </div>
                        <div>
                            <p className="text-xl font-bold text-center">Heure</p>
                            <div className="flex gap-2">
                                <input
                                    value={startTime}
                                    onChange={(e) => setStartTime(e.target.value)}
                                    className="w-1/2 text-center bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                    placeholder="Début"
                                />
                                -
                                <input
                                    value={endTime}
                                    onChange={(e) => setEndTime(e.target.value)}
                                    className="w-1/2 text-center bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                                    placeholder="Fin"
                                />
                            </div>
                        </div>
                    </div>
                </div>

                {/* Right content */}
                <div className="flex-1 flex flex-col gap-6">
                    {/* Description with poster */}
                    <div className="bg-zinc-200 flex-1 flex rounded-lg p-6 gap-6">
                        <div className="w-1/4 flex flex-col gap-2">
                            <img src={posterPreview} alt="Poster" className="w-full h-auto rounded-lg bg-zinc-300" />
                            <input
                                type="file"
                                accept="image/*"
                                onChange={(e) => {
                                    const file = e.target.files?.[0];
                                    if (file) {
                                        setPosterFile(file);
                                        setPosterPreview(URL.createObjectURL(file));
                                    }
                                }}
                                className="text-sm text-center bg-white border border-zinc-300 rounded-md px-2 py-1 outline-none focus:ring-2 focus:ring-indigo-400"
                            />
                        </div>
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            className="text-xl flex-1 bg-white border border-zinc-300 rounded-lg p-4 outline-none resize-none focus:ring-2 focus:ring-indigo-400"
                        />
                    </div>

                    {/* Buttons */}
                    <div className="flex gap-4">
                        <Link href="/event" className="flex-1">
                            <button className="w-full bg-green-400 hover:bg-green-500 text-black text-2xl py-4 rounded-lg">
                                Valider
                            </button>
                        </Link>
                        <Link href="/event" className="flex-1">
                            <button className="w-full bg-red-400 hover:bg-red-500 text-black text-2xl py-4 rounded-lg">
                                Annuler
                            </button>
                        </Link>
                    </div>
                </div>
            </main>
        </div>
    );
}
