export type Utilisateur = {
    id: number;
    nom: string;
    prenom: string;
    dateNaissance: string;
    email: string;
    ville: string;
    rue: string;
    codePostal: string;
};

export async function getAllUser(): Promise<Utilisateur[]> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs`,{
        //credentials: "include",
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Utilisateur[] = await res.json();
    return data;
}

export async function getMe(): Promise<Utilisateur> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/me`, {
        method: "GET",
        //credentials: "include",
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Utilisateur = await res.json();

    console.log(data);
    return data;
}

export async function getUserByID(id: number): Promise<Utilisateur> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs/${id}`, {
        method: "GET",
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data = await res.json();

    console.log(data);

    return data;
}