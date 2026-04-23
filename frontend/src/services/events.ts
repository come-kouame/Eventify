"use client";

export type Event = {
    id: number;
    titre: string;
    description: string;
    date: string;
    prix: number;
};

export async function getAllEvents(): Promise<Event[]> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/evenements`);

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Event[] = await res.json();
    return data;
}