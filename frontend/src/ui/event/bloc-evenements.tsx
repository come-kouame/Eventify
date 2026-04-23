"use client";
import {EventPresentation} from "@/ui/event/event-presentation";
import {getAllEvents, Event} from "@/services/events";
import {useEffect, useState} from "react";

export const BlocEvenements = () => {
    const [data, setData] = useState<Event[]>([]);

    useEffect(() => {
        getAllEvents()
            .then(setData)
            .catch(console.error);
    }, []);


    return (
        <div className="flex-1 flex justify-center flex-wrap gap-4">
            {data.map((event) => (
                <EventPresentation
                    key={event.id}
                    title={event.titre}
                    description={event.description}
                    date={event.date}
                    location="Lieu"
                    price={event.prix}
                    image={null}
                    place_init={100}
                    place_current={42}
                />
            ))}
        </div>
    )
};