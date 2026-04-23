"use client";
import { getAllUser, Utilisateur } from "@/services/user";
import { useEffect, useState } from "react";

export const LigneProfile = () => {
    const [users, setUsers] = useState<Utilisateur[]>([]);

    useEffect(() => {
        getAllUser()
            .then(setUsers)
            .catch(console.error);
    }, []);

    return (
        <>
            {users.map((user) => (
                <tr key={user.id} className="border-t">
                    <td className="p-2">{user.nom}</td>
                    <td className="p-2">{user.prenom}</td>
                    <td className="p-2">{user.email}</td>
                    <td className="p-2">Utilisateur</td>
                    <td className="p-2">
                        <a href={`/profiles/x`} className="bg-blue-500 text-white px-4 py-1 rounded">
                            Modifier
                        </a>
                    </td>
                </tr>
            ))}
        </>
    );
};