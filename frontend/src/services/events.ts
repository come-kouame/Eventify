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

export type Participant = {
    id: number;
    nom: string;
    prenom: string;
};

export async function getAllEvents(): Promise<Event[]> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements`,{
        //credentials: "include",
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Event[] = await res.json();
    return data;
}

export async function getAllValidEvents(): Promise<Event[]> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements/valides`,{
        //credentials: "include",
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Event[] = await res.json();
    return data;
}

export async function getEventByID(id: number): Promise<Event> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements/${id}`,{
        //credentials: "include",
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Event = await res.json();
    return data;
}

export async function getEventByUser(id: number): Promise<Event[]> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs/${id}/participations`,{
        //credentials: "include",
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Event[] = await res.json();
    return data;
}

export async function getParticipantByEventID(id: number): Promise<Participant[]> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements/${id}/participants`, {
        //credentials: "include",
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Participant[] = await res.json();

    console.log(data);

    return data;
}


export async function registerEvent(id: number, userId: number) {
    console.log(id);
    console.log(userId);
    const res = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/evenements/${id}/inscription?userId=${userId}`,
        {
            method: "PUT",
        }
    );


    return res.status;
}

export async function createEvent(eventPayload: {
    titre: string;
    description: string;
    date_debut: string;
    date_fin: string;
    lieu: string;
    adresse: string;
    prix: number;
    nb_places: number;
    poster: string;
    nb_places_vendues: number;
    orga_id: number | null
}) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(eventPayload),
        //credentials: "include",
    });


    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    return await res.json();
}

export async function valideEvent(id: number, eventPayload: {
    titre: string;
    description: string;
    date_debut: string;
    date_fin: string;
    lieu: string;
    adresse: string;
    prix: number;
    nb_places: number;
    poster: string;
    nb_places_vendues: number;
    valide: boolean;
    orga_id: number | null;
    admin_id: number | null
}) {
    const updateRes = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(eventPayload),
        //credentials: "include",
    });

    if (!updateRes.ok) {
        throw new Error(`Erreur update ${updateRes.status}`);
    }

    return await updateRes.json();
}

export async function deleteEvent(id: number) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements/${id}`, {
        method: "DELETE",
        //credentials: "include",
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