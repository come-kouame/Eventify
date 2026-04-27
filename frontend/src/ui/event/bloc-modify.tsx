"use client";
import {EventModify} from "@/ui/event/event-modify";
import {getAllEvents, Event} from "@/services/events";
import {useEffect, useState} from "react";

export const BlocModify = () => {
    const [data, setData] = useState<Event[]>([]);

    useEffect(() => {
        getAllEvents()
            .then(setData)
            .catch(console.error);
    }, []);


    return (
        <div className="flex-1 flex justify-center flex-wrap gap-4">
            {data.map((event) => (
                <EventModify
                    key={event.id}
                    id={event.id}
                    title={event.titre}
                    description={event.description}
                    date={event.date_debut}
                    location={event.lieu}
                    price={event.prix}
                    image={event.poster}
                    place_init={event.nb_places}
                    place_current={event.nb_places - event.nb_places_vendues}
                />
            ))}
        </div>
    )
};