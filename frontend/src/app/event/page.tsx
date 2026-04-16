import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected"
import {EventPresentation} from "@/ui/event/event-presentation";
import {NavBar} from "@/ui/nav-bar/nav-bar";

export default function Home() {
    return (
        <div className="min-h-screen bg-zinc-100 font-sans">
            <NavBar/>

            {/* Title */}
            <h1 className="text-5xl text-center py-10">
                Les Événements organisés
            </h1>

            <div className="flex px-10 gap-6">

                {/* Left panel */}
                <div className="w-1/4 flex flex-col gap-6 rounded-lg">

                    {/* Create event button */}
                    <a href="/event/create" className="py-4 text-xl rounded-lg bg-indigo-300 text-center">
                        Créer un event
                    </a>

                    {/* Filters */}
                    <div className="bg-zinc-300 p-6 flex flex-col gap-4 rounded-lg">
                        <h2 className="text-3xl text-center">Filtres</h2>
                        <div className="text-lg flex flex-col gap-3">
                            <label className="flex items-center gap-2">
                                <input type="checkbox" />
                                En cours
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox" />
                                À valider
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox" />
                                Annulé
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox" />
                                Passé
                            </label>
                        </div>
                    </div>
                </div>

                {/* Right panel */}
                <div className="flex-1 bg-zinc-300 flex flex-col gap-4 p-6 rounded-lg">
                    <EventPresentation
                        title="Titre de l’événement"
                        description="Lorem ipsum dolor sit amet, consectetur adipiscing elit..."
                        date="Jeudi 19 Novembre 2014"
                        location="Lieu"
                        price={9}
                        image={null}
                        place_init={100}
                        place_current={42}
                    />
                    <EventPresentation
                        title="Titre de l’événement"
                        description="Lorem ipsum dolor sit amet, consectetur adipiscing elit..."
                        date="Jeudi 19 Novembre 2014"
                        location="Lieu"
                        price={9}
                        image={null}
                        place_init={100}
                        place_current={42}
                    />
                </div>

            </div>
        </div>
    );
}
