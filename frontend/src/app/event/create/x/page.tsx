import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected"

export default function Home() {
    return (
        <div className=" justify-center bg-zinc-50 font-sans">
            <NavBarNotConnected/>

            <h1 className="font-medium text-black text-6xl text-center w-full py-10">
                Nom de l’évènement
            </h1>
            <main className="flex min-h-screen w-full gap-8 px-16">
                {/* Left info panel */}
                <div className="w-1/4 bg-zinc-200 p-6 flex items-center justify-center text-center">
                    <p className="text-xl">
                        Info importantes <br/> (dates, lieu, prix, etc.)
                    </p>
                </div>

                {/* Right content */}
                <div className="flex-1 flex flex-col gap-6">
                    {/* Description */}
                    <div className="bg-zinc-200 flex-1 flex items-center justify-center">
                        <p className="text-4xl text-center">
                            Description de l’évènements
                        </p>
                    </div>

                    {/* Buttons */}
                    <div className="flex gap-4">
                        <button className="flex-1 bg-red-600 text-black text-3xl py-6">
                            Annuler
                        </button>
                        <button className="flex-1 bg-green-600 text-black text-3xl py-6">
                            Valider
                        </button>
                    </div>
                </div>
            </main>
        </div>
    );
}
