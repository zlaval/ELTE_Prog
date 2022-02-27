﻿#include "MyApp.h"

#include <math.h>
#include <vector>

#include <array>
#include <list>
#include <tuple>

#include "imgui\imgui.h"

#include "Includes/ObjParser_OGL3.h"

CMyApp::CMyApp()
{
}


CMyApp::~CMyApp()
{
	
}

bool CMyApp::Init()
{
	// törlési szín legyen fehér
	glClearColor(1,1,1, 1);

	glEnable(GL_CULL_FACE); // kapcsoljuk be a hatrafele nezo lapok eldobasat
	glEnable(GL_DEPTH_TEST); // mélységi teszt bekapcsolása (takarás)
	//glDepthMask(GL_FALSE); // kikapcsolja a z-pufferbe történő írást - https://www.khronos.org/registry/OpenGL-Refpages/gl2.1/xhtml/glDepthMask.xml

	// átlátszóság engedélyezése
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // meghatározza, hogy az átlátszó objektum az adott pixelben hogyan módosítsa a korábbi fragmentekből oda lerakott színt: https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/glBlendFunc.xhtml

	//
	// shaderek betöltése
	//

	// így is létre lehetne hozni:

	// a shadereket tároló program létrehozása
	m_program.Init({
		{ GL_VERTEX_SHADER, "myVert.vert" },
		{ GL_FRAGMENT_SHADER, "myFrag.frag" }
	},
	{
		{ 0, "vs_in_pos" },					// VAO 0-as csatorna menjen a vs_in_pos-ba
		{ 1, "vs_in_normal" },				// VAO 1-es csatorna menjen a vs_in_normal-ba
		{ 2, "vs_out_tex0" },				// VAO 2-es csatorna menjen a vs_in_tex0-ba
	});

	//
	// geometria definiálása (std::vector<...>) és GPU pufferekbe (m_buffer*) való feltöltése BufferData-val
	//

	// vertexek pozíciói:
	/*
	Az m_gpuBufferPos konstruktora már létrehozott egy GPU puffer azonosítót és a most következő BufferData hívás ezt 
		1. bind-olni fogja GL_ARRAY_BUFFER target-re (hiszen m_gpuBufferPos típusa ArrayBuffer) és
		2. glBufferData segítségével áttölti a GPU-ra az argumentumban adott tároló értékeit

	*/
	m_gpuBufferPos.BufferData( 
		std::vector<glm::vec3>{
			//		  X, Y, Z
			glm::vec3(0,-2,-2),	// 0-ás csúcspont
			glm::vec3(0, 2,-2), // 1-es
			glm::vec3(0,-2, 2), // 2-es
			glm::vec3(0, 2, 2)  // 3-as
		}
	);

	// normálisai
	m_gpuBufferNormal.BufferData(
		std::vector<glm::vec3>{
			// normal.X,.Y,.Z
			glm::vec3(1, 0, 0), // 0-ás csúcspont
			glm::vec3(1, 0, 0), // 1-es
			glm::vec3(1, 0, 0), // 2-es
			glm::vec3(1, 0, 0)  // 3-as
		}
	);

	// textúrakoordinátái
	m_gpuBufferTex.BufferData(
		std::vector<glm::vec2>{
			//        u, v
			glm::vec2(0, 0), // 0-ás csúcspont
			glm::vec2(1, 0), // 1-es
			glm::vec2(0, 1), // 2-es
			glm::vec2(1, 1)	 // 3-as
		}
	);

	// és a primitíveket alkotó csúcspontok indexei (az előző tömbökből) - triangle list-el való kirajzolásra felkészülve
	m_gpuBufferIndices.BufferData(
		std::vector<int>{
			0,1,2,	// 1. háromszög: 0-1-2 csúcspontokból
			2,1,3	// 2. háromszög: 2-1-3 csúcspontokból
		}
	);

	// geometria VAO-ban való regisztrálása
	m_vao.Init(
		{
			{ CreateAttribute<0, glm::vec3, 0, sizeof(glm::vec3)>, m_gpuBufferPos },	// 0-ás attribútum "lényegében" glm::vec3-ak sorozata és az adatok az m_gpuBufferPos GPU pufferben vannak
			{ CreateAttribute<1, glm::vec3, 0, sizeof(glm::vec3)>, m_gpuBufferNormal },	// 1-es attribútum "lényegében" glm::vec3-ak sorozata és az adatok az m_gpuBufferNormal GPU pufferben vannak
			{ CreateAttribute<2, glm::vec2, 0, sizeof(glm::vec2)>, m_gpuBufferTex }		// 2-es attribútum "lényegében" glm::vec2-ők sorozata és az adatok az m_gpuBufferTex GPU pufferben vannak
		},
		m_gpuBufferIndices
	);

	// textúra betöltése
	m_textureMetal.FromFile("Assets/texture.png");

	// mesh betöltése
	m_mesh = std::unique_ptr<Mesh>(ObjParser::parse("Assets/suzanne.obj"));
	m_mesh->initBuffers();

	// kamera
	m_camera.SetProj(glm::radians(60.0f), 640.0f / 480.0f, 0.01f, 1000.0f);

	return true;
}

void CMyApp::Clean()
{
}

void CMyApp::Update()
{
	static Uint32 last_time = SDL_GetTicks();
	float delta_time = (SDL_GetTicks() - last_time) / 1000.0f;

	m_camera.Update(delta_time);

	last_time = SDL_GetTicks();
}

void CMyApp::Render()
{
	// töröljük a frampuffert (GL_COLOR_BUFFER_BIT) és a mélységi Z puffert (GL_DEPTH_BUFFER_BIT)
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// mindegyik geometria ugyanazt a shader programot hasznalja: ne kapcsolgassuk most ki-be
	m_program.Use();

	m_program.SetTexture("texImage", 0, m_textureMetal);
	
	// Suzanne 1
	glm::mat4 suzanne1World = glm::translate(glm::vec3(-2, 0, 0));
	m_program.SetUniform("world", suzanne1World);
	m_program.SetUniform("worldIT", glm::transpose(glm::inverse(suzanne1World)));
	m_program.SetUniform("MVP", m_camera.GetViewProj()*suzanne1World);
	m_program.SetUniform("Kd", glm::vec4(1, 0.3, 0.3, 1));

	m_mesh->draw();

	// fal

	// most kikapcsoljuk a hátlapeldobást, hogy lássuk mindkétt oldalát!
	// Feladat: még mi kellene ahhoz, hogy a megvilágításból származó színek jók legyenek?
	glDisable(GL_CULL_FACE);

	glm::mat4 wallWorld = glm::translate(glm::vec3(0, 0, 0));
	m_program.SetUniform("world", wallWorld);
	m_program.SetUniform("worldIT", glm::transpose(glm::inverse(wallWorld)));
	m_program.SetUniform("MVP", m_camera.GetViewProj()*wallWorld);
	m_program.SetUniform("Kd", m_wallColor);

	m_vao.Bind();
	glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
	m_vao.Unbind(); // nem feltétlen szükséges: a m_mesh->draw beállítja a saját VAO-ját

	// de kapcsoljuk most vissza, mert még jön egy Suzanne
	glEnable(GL_CULL_FACE);

	// Suzanne 2
	glm::mat4 suzanne2World = glm::translate(glm::vec3( 2, 0, 0));
	m_program.SetUniform("world", suzanne2World);
	m_program.SetUniform("worldIT", glm::transpose(glm::inverse(suzanne2World)));
	m_program.SetUniform("MVP", m_camera.GetViewProj()*suzanne2World);
	m_program.SetUniform("Kd", glm::vec4(0.3, 1, 0.3, 1));

	m_mesh->draw();

	// végetért a 3D színtér rajzolása
	m_program.Unuse();

	//
	// UI
	//
	// A következő parancs megnyit egy ImGui tesztablakot és így látszik mit tud az ImGui.
	ImGui::ShowTestWindow();
	// A ImGui::ShowTestWindow implementációja az imgui_demo.cpp-ben található
	// Érdemes még az imgui.h-t böngészni, valamint az imgui.cpp elején a FAQ-ot elolvasni.
	// Rendes dokumentáció nincs, de a fentiek elegendőek kell legyenek.

	ImGui::SetNextWindowPos(ImVec2(300, 400), ImGuiSetCond_FirstUseEver);
	if (ImGui::Begin("Tesztablak"))
	{
		ImGui::Text("Fal (RGBA)");
		ImGui::SliderFloat4("Fal kd", &(m_wallColor[0]), 0, 1);
	}
	ImGui::End();
}

void CMyApp::KeyboardDown(SDL_KeyboardEvent& key)
{
	m_camera.KeyboardDown(key);
}

void CMyApp::KeyboardUp(SDL_KeyboardEvent& key)
{
	m_camera.KeyboardUp(key);
}

void CMyApp::MouseMove(SDL_MouseMotionEvent& mouse)
{
	m_camera.MouseMove(mouse);
}

void CMyApp::MouseDown(SDL_MouseButtonEvent& mouse)
{
}

void CMyApp::MouseUp(SDL_MouseButtonEvent& mouse)
{
}

void CMyApp::MouseWheel(SDL_MouseWheelEvent& wheel)
{
}

// a két paraméterbe az új ablakméret szélessége (_w) és magassága (_h) található
void CMyApp::Resize(int _w, int _h)
{
	glViewport(0, 0, _w, _h );

	m_camera.Resize(_w, _h);
}