import Image from "next/image";

export const NavBarNotConnected = () => {
    return (
        <>
            <div className="flex items-center justify-between p-4 bg-gray-200">
                <p className="font-bold text-5xl leading-tight">
                    Eventify
                </p>

                <div className="flex text-xl space-x-4">
                    <a>Se connecter</a>
                    <a>S'inscrire</a>
                </div>
            </div>
            <div className="h-[2px] bg-black"></div>
        </>
    );
};
