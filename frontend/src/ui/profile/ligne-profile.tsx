"use client";
import {getAllUser, getStatut, Utilisateur} from "@/services/user";
import { useEffect, useState } from "react";

export const LigneProfile = ({ filters }: { filters: string[] }) => {
    const [users, setUsers] = useState<Utilisateur[]>([]);

    useEffect(() => {
        getAllUser()
            .then(setUsers)
            .catch(console.error);
    }, []);

    return (
        <>
            {users
                .filter((user) => filters.length === 0 || filters.includes(getStatut(user)))
                .map((user) => (
                    <tr key={user.id} className="border-t">
                        <td className="p-2">{user.nom}</td>
                        <td className="p-2">{user.prenom}</td>
                        <td className="p-2">{user.email}</td>
                        <td className="p-2">{getStatut(user)}</td>
                        <td className="p-2">
                            <a href={`/profiles/${user.id}`} className="bg-blue-500 text-white px-4 py-1 rounded">
                                Modifier
                            </a>
                        </td>
                    </tr>
                ))}
        </>
    );
};