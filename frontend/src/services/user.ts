export type Utilisateur = {
    id: number;
    id_orga: number | null;
    id_admin: number | null;
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

export const getStatut = (user: Utilisateur) => {
    if (user.id_admin != null) return "Administrateur";
    if (user.id_orga != null) return "Organisateur";
    return "Utilisateur";
};

export async function promoteToOrga(id: number) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs/${id}/promote-orga`, {
        method: "POST",
    });

    if (!res.ok) throw new Error("Erreur promoteOrga");
    return res.json();
}

export async function promoteToAdmin(id: number) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs/${id}/promote-admin`, {
        method: "POST",
    });

    if (!res.ok) throw new Error("Erreur promoteAdmin");
    return res.json();
}

export async function demoteToOrga(id: number) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs/${id}/demote-orga`, {
        method: "POST",
    });

    if (!res.ok) throw new Error("Erreur demoteOrga");
    return res.json();
}

export async function demoteToUser(id: number) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs/${id}/demote-user`, {
        method: "POST",
    });

    if (!res.ok) throw new Error("Erreur demoteUser");
    return res.json();
}