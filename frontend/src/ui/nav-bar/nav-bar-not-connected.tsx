import Image from "next/image";

export const NavBarNotConnected = () => {
    return (
        <>
            <div className="flex items-center justify-between p-4 bg-gray-200">
                <a href="/" className="font-bold text-5xl leading-tight">
                    Eventify
                </a>

                <div className="flex text-xl space-x-4">
                    <a href="/login">Se connecter</a>
                    <a href="/signup">S'inscrire</a>
                </div>
            </div>
            <div className="h-[2px] bg-black"></div>
        </>
    );
};
