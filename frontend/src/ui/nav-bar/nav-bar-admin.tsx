import { LogoutButton } from "./LogoutButton";
import Link from "next/link";
export const NavBarAdmin = () => {
    return (
        <>
            <div className="flex items-center justify-between p-4 bg-gray-200">
                <Link href="/" className="font-bold text-5xl leading-tight">
                    Eventify
                </Link>

                <div className="flex text-xl space-x-4">
                    <Link href="/event/create">Créer un évènement</Link>
                    <Link href="/event/manage">Gérer les évènements</Link>
                    <Link href="/profiles">Gérer les comptes</Link>
                    <Link href="/event">Mes évènements</Link>
                    <Link href="/profiles/me">Mon Compte</Link>
                     <LogoutButton />
                </div>
            </div>
            <div className="h-[1px] bg-black"></div>
        </>
    );
};
