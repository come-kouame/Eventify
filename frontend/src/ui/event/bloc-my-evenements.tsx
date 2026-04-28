"use client";
import {EventPresentation} from "@/ui/event/event-presentation";
import {getEventByUser, Event} from "@/services/events";
import {useEffect, useState} from "react";
import {getMe, Utilisateur} from "@/services/user";

export const BlocMyEvenements = () => {
    const [me, setMe] = useState<Utilisateur | null>(null);

    useEffect(() => {
        getMe()
            .then(setMe)
            .catch((err) => console.error("getMe error:", err));
    }, []);

    const [data, setData] = useState<Event[]>([]);

    useEffect(() => {
        if (!me) return;

        getEventByUser(me.id)
            .then(setData)
            .catch(console.error);
    }, [me]);


    return (
        <div className="flex-1 flex justify-center flex-wrap gap-4">
            {data.map((event) => (
                <EventPresentation
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