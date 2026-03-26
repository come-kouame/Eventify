"use client";

export function FormConnexion() {
    return (
        <div className="flex items-center justify-center px-6 mx-auto w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-2xl xl:p-0 dark:bg-gray-800 dark:border-gray-700">
            <div className="p-6 space-y-6 sm:p-8 w-full">
                <form className="space-y-4 md:space-y-6" action="#">
                    {/* Email et Mot de passe sur la même ligne */}
                    <div className="flex gap-4">
                        {/* Email */}
                        <div className="flex-1">
                            <label
                                htmlFor="email"
                                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                            >
                                E-Mail
                            </label>
                            <input
                                type="email"
                                name="email"
                                id="email"
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-400 dark:focus:border-blue-400"
                                placeholder="nom@email.fr"
                                required
                            />
                        </div>

                        {/* Mot de passe */}
                        <div className="flex-1">
                            <label
                                htmlFor="password"
                                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                            >
                                Mot de Passe
                            </label>
                            <input
                                type="password"
                                name="password"
                                id="password"
                                placeholder="••••••••"
                                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-3 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-400 dark:focus:border-blue-400"
                                required
                            />
                        </div>
                    </div>

                    {/* Mot de passe oublié */}
                    <div className="flex items-center justify-end">
                        <a
                            href="#"
                            className="text-sm font-medium text-blue-600 hover:underline dark:text-blue-500"
                        >
                            Mot de passe oublié ?
                        </a>
                    </div>

                    {/* Bouton bleu */}
                    <button
                        type="submit"
                        className="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium rounded-lg px-5 py-3 text-center transition duration-200 focus:outline-none focus:ring-4 focus:ring-blue-300 dark:focus:ring-blue-500"
                    >
                        Connexion
                    </button>

                    {/* Lien inscription */}
                    <p className="text-sm font-light text-gray-500 dark:text-gray-400 text-center">
                        Pas encore de compte ?
                        <a
                            href="#"
                            className="font-medium text-blue-600 hover:underline ml-1 dark:text-blue-500"
                        >
                            S'inscrire
                        </a>
                    </p>
                </form>
            </div>
        </div>
    );
}