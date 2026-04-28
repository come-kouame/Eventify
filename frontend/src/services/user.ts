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

export async function modifyUser(id: number, userData: Omit<Utilisateur, "id" | "id_admin" | "id_orga">): Promise<Utilisateur> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
    });

    if (!res.ok) {
        throw new Error(`Erreur ${res.status}`);
    }

    const data: Utilisateur = await res.json();
    return data;
}

export const getStatut = (user: Utilisateur) => {
    if (user.id_admin != null) return "Administrateur";
    if (user.id_orga != null) return "Organisateur";
    return "Utilisateur";
};

export async function setAdmin(id: number) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs/${id}/set-to-admin`, {
        method: "PUT",
    });

    if (!res.ok) throw new Error("Erreur setAdmin");
    return res.json();
}

export async function setOrga(id: number) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs/${id}/set-to-orga`, {
        method: "PUT",
    });

    if (!res.ok) throw new Error("Erreur setOrga");
    return res.json();
}

export async function setUser(id: number) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/utilisateurs/${id}/set-to-user`, {
        method: "PUT",
    });

    if (!res.ok) throw new Error("Erreur setUser");
    return res.json();
}