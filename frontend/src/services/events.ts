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

export async function getAllValidEvents(): Promise<Event[]> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements/valides`);

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

export async function createEvent(eventPayload: Partial<Event>) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(eventPayload),
    });


    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    return await res.json();
}

export async function valideEvent(id: number, eventPayload: Partial<Event>) {
    const updateRes = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(eventPayload),
    });

    if (!updateRes.ok) {
        throw new Error(`Erreur update ${updateRes.status}`);
    }

    return await updateRes.json();
}

export async function deleteEvent(id: number) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements/${id}`, {
        method: "DELETE",
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    if (res.status === 204) {
        return null;
    }

    const text = await res.text();
    return text ? JSON.parse(text) : null;
}