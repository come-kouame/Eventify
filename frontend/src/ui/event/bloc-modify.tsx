"use client";
import {EventModify} from "@/ui/event/event-modify";
import {getAllEvents, Event} from "@/services/events";
import {useEffect, useState} from "react";

interface Props {
    filters: {
        enCours: boolean;
        aValider: boolean;
        passe: boolean;
    };
}

export const BlocModify = ({ filters }: Props) => {
    const [data, setData] = useState<Event[]>([]);

    useEffect(() => {
        getAllEvents()
            .then(setData)
            .catch(console.error);
    }, []);

    const filteredData = data.filter((event) => {
        const isPasse = new Date(event.date_debut) < new Date();
        const isAValider = event.admin_id == null;
        const isEnCours = !isPasse && !isAValider;

        const noFilterSelected = !filters.enCours && !filters.aValider && !filters.passe;
        if (noFilterSelected) return true;

        return (
            (filters.enCours && isEnCours) ||
            (filters.aValider && isAValider) ||
            (filters.passe && isPasse)
        );
    });

    return (
        <div className="flex-1 flex justify-center flex-wrap gap-4">
            {filteredData.map((event) => (
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
                    admin_id={event.admin_id}
                />
            ))}
        </div>
    )
};