<x-guest-layout>

    <div class="container mx-auto p-3">
        <div class="mb-5">
            <a href="/" class="text-blue-400 hover:text-blue-600 hover:underline"><i
                    class="fas fa-long-arrow-alt-left"></i> Vissza a főoldalra</a>
        </div>
        <div class="grid grid-cols-1 gap-5">
            <div class="col-auto mx-auto">
                @if($item->image)
                    <img class="object-cover w-full" src="{{ asset('image/'.$item->image) }}" style="height:400px;">
                @else
                    <p class="border-l border-r w-full border-t bg-gray-400 font-semibold" style="height:400px;">
                        Nincs kép
                    </p>
                @endif

            </div>

            <div class="px-2.5 py-2 ">
                <h1 class="text-3xl mb-0.5 font-semibold">
                    {{$item -> name}}
                </h1>
                <p class="text-gray-800 mt-5 text-xl p-5 ">
                    {{$item -> description}}
                </p>
            </div>
            <div class="flex flex-row flex-wrap gap-2 mt-3">
                @foreach($labels as $l)
                    <span class="py-0.5 px-1.5 font-semibold text-sm"
                          style="background-color: {{$l -> color}};">{{ $l -> name}}</span>
                @endforeach
            </div>
            @auth
                @if(Auth::user()->is_admin)
                    <div class="flex justify-center">
                        <a href="{{ route('items.edit', $item) }}"
                           class="bg-green-500 hover:bg-green-700 px-2 py-2 rounded text-white"><i
                                class="fas fa-plus-circle p-1"></i>Szerkeszt</a>

                        <form method="POST" action="{{ route('items.destroy', $item) }}" id="delete-item"
                              class="bg-red-500 hover:bg-red-700 px-2 mx-2 py-2 rounded text-white "
                        >
                            @csrf
                            @method('DELETE')
                            <a href=""
                               onclick="event.preventDefault(); document.querySelector('#delete-item').submit(); "
                            ><i class="fa fa-trash p-1"></i>Töröl</a>
                        </form>
                    </div>
                @endif
            @endauth
            <div class="grid grid-cols-1 gap-5">
                @forelse ($comments as $c)
                    <div class="col-auto border border-gray-400">
                        <h1 class="text-xl font-semibold my-4 mx-3">
                            {{$c->user ->name}}
                        </h1>
                        <p class="mt-1 text-gray-800 my-2 mx-3">
                            {{ $c -> text }}
                        </p>
                        @if(Auth::user()!=null && (Auth::user()->id == $c->user->id || Auth::user()->is_admin))
                            <div class="flex justify-end my-3 mx-3">
                                <form method="POST" action="{{ route('comments.destroy', $c) }}" id="delete-comment-{{$c->id}}"
                                      class="bg-red-500 hover:bg-red-700 px-2 mx-1 py-1 rounded text-white"
                                >
                                    @csrf
                                    @method('DELETE')
                                    <a href=""
                                       onclick="event.preventDefault(); document.querySelector('#delete-comment-{{$c->id}}').submit(); "
                                       ><i
                                            class="fa fa-xmark p-1"></i>Töröl</a>
                                </form>
                                <a href="{{ route('comments.edit', $c) }}"
                                   class="bg-green-500 hover:bg-green-700 px-2 mx-1 py-1 rounded text-white"><i
                                        class="fa fa-xmark p-1"></i>Szerkeszt</a>
                            </div>
                        @endif
                    </div>
                @empty
                    <div class="col-span-3 bg-red-200 text-center rounded-lg py-1">
                        Nem található komment!
                    </div>
                @endforelse
            </div>
            @auth
                <div class="w-full">
                    <form action="{{ route('comments.store') }}" method="POST">
                        @csrf
                        <label class="block font-medium text-gray-700">Új hozzászólás</label>
                        <input type="hidden" value="{{ $item->id }}" name="item_id">
                        <textarea
                            rows="5"
                            name="text"
                            class="mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded"></textarea>
                        <div class="w-full flex justify-end">
                            <button type="submit"
                                    class="mt-4 bg-blue-500 hover:bg-blue-600 text-gray-100 font-semibold px-1 py-1 text-xl">
                                Elküld
                            </button>
                        </div>
                    </form>
                </div>
            @endauth

        </div>

    </div>


</x-guest-layout>
