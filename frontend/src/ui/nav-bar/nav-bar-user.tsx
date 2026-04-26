import Image from "next/image";
import { LogoutButton } from "./LogoutButton";

export const NavBarUser = () => {
    return (
        <>
            <div className="flex items-center justify-between p-4 bg-gray-200">
                <a href="/" className="font-bold text-5xl leading-tight">
                    Eventify
                </a>

                <div className="flex text-xl space-x-4">
                    <a href="/event">Mes évènements</a>
                    <a href="/profiles/x">Mon Compte</a>
                     <LogoutButton />
                </div>
            </div>
            <div className="h-[1px] bg-black"></div>
        </>
    );
};
