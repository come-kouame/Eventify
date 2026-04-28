"use client";
import {EventPresentation} from "@/ui/event/event-presentation";
import {Event} from "@/services/events";

export const BlocEvenements = ({ data }: { data: Event[] }) => {

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