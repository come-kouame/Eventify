"use client";

import { useEffect, useState } from "react";
import {NavBarAdmin} from "@/ui/nav-bar/nav-bar-admin";
import {NavBarOrga} from "@/ui/nav-bar/nav-bar-orga";
import {NavBarUser} from "@/ui/nav-bar/nav-bar-user";
import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected";
import {getMe, getStatut} from "@/services/user";

export const NavBar = () => {
    const [status, setStatus] = useState<string | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchUserStatus = async () => {
            try {
                const user = await getMe();

                if (!user) {
                    setStatus(null);
                } else {
                    const userStatus = getStatut(user);
                    if (userStatus === "Administrateur") setStatus("admin");
                    else if (userStatus === "Organisateur") setStatus("orga");
                    else setStatus("user");
                }
            } catch {
                setStatus(null);
            } finally {
                setLoading(false);
            }
        };

        fetchUserStatus();
    }, []);

    if (loading) return null;

    return (
        <>
            {status === "admin" && <NavBarAdmin/>}
            {status === "orga" && <NavBarOrga/>}
            {status === "user" && <NavBarUser/>}
            {status === null && <NavBarNotConnected/>}
        </>
    );
};