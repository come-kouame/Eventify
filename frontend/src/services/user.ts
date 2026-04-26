"use client";

export type Utilisateur = {
    id: number;
    nom: string;
    prenom: string;
    email: string;
    ville: string;
};

export async function getAllUser(): Promise<Utilisateur[]> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs`);

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Utilisateur[] = await res.json();
    return data;
}

export async function getMe(): Promise<Utilisateur> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/me`, {
        method: "GET",
        credentials: "include",
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Utilisateur = await res.json();
    return data;
}