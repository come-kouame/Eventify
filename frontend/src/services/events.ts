"use client";

export type Event = {
    id: number;
    titre: string;
    description: string;
    date_debut: string;
    date_fin: string;
    lieu: string;
    adresse: string;
    poster: string;
    prix: number;
    nb_places: number;
    nb_places_vendues: number;
    valide: boolean;
    orga_id: number;
    admin_id: number | null;
};

export async function getAllEvents(): Promise<Event[]> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements`);

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Event[] = await res.json();
    return data;
}

export async function getEventByID(id: number): Promise<Event> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements/${id}`);

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Event = await res.json();
    return data;
}