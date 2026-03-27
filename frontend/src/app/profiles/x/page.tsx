import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected";

export default function Home() {
    return (
        <div className="min-h-screen font-sans">
            {/* Header */}
            <NavBarNotConnected/>

            {/* Title */}
            <h1 className="text-center text-5xl pt-6 font-medium mb-8">
                Profil de NOM PRENOM
            </h1>

            <div className="px-6">
                <div className="bg-gray-300 p-6">
                    <div className="grid grid-cols-2 gap-8 text-xl">
                        {/* Left column */}
                        <div className="space-y-4">
                            <div>
                                <label>Nom :</label>
                                <input type="text" className="w-full p-2 mt-1 bg-gray-100" />
                            </div>
                            <div>
                                <label>Prénom :</label>
                                <input type="text" className="w-full p-2 mt-1 bg-gray-100" />
                            </div>
                            <div>
                                <label>E-mail :</label>
                                <input type="email" className="w-full p-2 mt-1 bg-gray-100" />
                            </div>
                            <div>
                                <label>Mot de passe :</label>
                                <input type="password" className="w-full p-2 mt-1 bg-gray-100" />
                            </div>
                        </div>

                        {/* Right column */}
                        <div className="space-y-4">
                            <div>
                                <label>Adresse :</label>
                                <input type="text" className="w-full p-2 mt-1 bg-gray-100" />
                            </div>
                            <div>
                                <label>Ville :</label>
                                <input type="text" className="w-full p-2 mt-1 bg-gray-100" />
                            </div>
                            <div>
                                <label>Code postal :</label>
                                <input type="text" className="w-full p-2 mt-1 bg-gray-100" />
                            </div>
                            <div>
                                <label>Numéro de tel :</label>
                                <input type="text" className="w-full p-2 mt-1 bg-gray-100" />
                            </div>
                        </div>
                    </div>

                    {/* Status */}
                    <div className="flex justify-between items-center mt-8 text-xl">
                        <span>Statut :</span>
                        <label className="flex items-center gap-2">
                            <input type="radio" name="status" /> Administrateur
                        </label>
                        <label className="flex items-center gap-2">
                            <input type="radio" name="status" defaultChecked /> Organisateur
                        </label>
                        <label className="flex items-center gap-2">
                            <input type="radio" name="status" /> Participant
                        </label>
                    </div>

                    {/* Buttons */}
                    <div className="flex gap-4 mt-8">
                        <a href="/profiles" className="flex-1 bg-green-600 text-black text-3xl py-4 text-center block">
                            Valider
                        </a>
                        <a href="/profiles" className="flex-1 bg-red-600 text-black text-3xl py-4 text-center block">
                            Annuler
                        </a>
                    </div>
                </div>
            </div>
        </div>
    );
}
