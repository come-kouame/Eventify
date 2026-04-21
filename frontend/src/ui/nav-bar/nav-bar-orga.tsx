import Image from "next/image";

export const NavBarOrga = () => {
    return (
        <>
            <div className="flex items-center justify-between p-4 bg-gray-200">
                <a href="/" className="font-bold text-5xl leading-tight">
                    Eventify
                </a>

                <div className="flex text-xl space-x-4">
                    <a href="/event/create">Créer un évènement</a>
                    <a href="/event">Mes évènements</a>
                    <a href="/profiles/x">Mon Compte</a>
                    <a href="">Déconnexion</a>
                </div>
            </div>
            <div className="h-[1px] bg-black"></div>
        </>
    );
};
