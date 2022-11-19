<x-guest-layout>
    <div class="container mx-auto p-3 lg:px-36">
        <form action="{{ route('comments.update', $comment) }}" method="POST">
            @csrf
            @method('PATCH')
            <label class="block font-medium text-xl text-gray-700">Hozzászólás módosítása</label>

            <textarea
                rows="5"
                name="text"
                class="mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded">{{$comment->text}}</textarea>
            <div class="w-full flex justify-end">
                <button type="submit"
                        class="mt-4 bg-blue-500 hover:bg-blue-600 text-gray-100 font-semibold rounded px-1 py-1 text-xl">
                    Mentés
                </button>
            </div>
        </form>
    </div>
</x-guest-layout>
