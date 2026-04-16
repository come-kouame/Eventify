"use client";

import {Calendar} from '@/ui/calendar/calendar';
import {NavBar} from "@/ui/nav-bar/nav-bar";

export default function Home() {
    const eventDate = new Date(2026, 3, 7);

    return (
        <div className="justify-center bg-zinc-50 font-sans">
            <NavBar/>

            <h1 className="font-medium text-black text-6xl text-center w-full py-10">
                Nom de l’évènement
            </h1>
            <main className="flex w-full gap-8 px-16">
                {/* Left info panel */}
                <div className="w-1/4 bg-zinc-200 p-6 flex flex-col items-center justify-start text-center rounded-lg gap-6">
                    {/* Calendar */}
                    <Calendar
                        date={eventDate}
                        startDate={null}
                        endDate={null}
                        onSelectDate={() => {}}
                        className="w-full"
                    />

                    {/* Informations importantes */}
                    <div className="flex flex-col gap-6 w-full text-center">
                        <div>
                            <p className="text-xl font-bold text-center">Lieu</p>
                            <p>Palais des Congrès, Lyon</p>
                        </div>
                        <div>
                            <p className="text-xl font-bold text-center">Prix</p>
                            <p>20€</p>
                        </div>
                        <div>
                            <p className="text-xl font-bold text-center">Heure</p>
                            <p>18:00 - 22:00</p>
                        </div>
                    </div>
                </div>

                {/* Right content */}
                <div className="flex-1 flex flex-col gap-6">
                    {/* Description with poster */}
                    <div className="bg-zinc-200 flex-1 flex rounded-lg p-6">
                        <img src="/poster-example.jpg" alt="Poster" className="w-1/4 h-auto rounded-lg mr-6 bg-zinc-300" />
                        <p className="text-4xl text-center flex-1 flex items-center justify-center">
                            Description de l’évènements
                        </p>
                    </div>

                    {/* Button */}
                    <button className="bg-indigo-300 text-black text-3xl py-6 rounded-lg">
                        Je m’inscrit
                    </button>
                </div>
            </main>
        </div>
    );
}
