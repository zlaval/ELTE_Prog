#include "MyApp.h"

#include <math.h>
#include <vector>

#include <array>
#include <list>
#include <tuple>
#include <imgui/imgui.h>
#include "includes/GLUtils.hpp"

CMyApp::CMyApp(void)
{
	m_camera.SetView(glm::vec3(30, 0, 0), glm::vec3(0, 0, 0), glm::vec3(0, 1, 0));
	m_mesh = nullptr;
}

CMyApp::~CMyApp(void)
{
}


void CMyApp::InitSkyBox()
{
	m_SkyboxPos.BufferData(
		std::vector<glm::vec3>{
		// hátsó lap
		glm::vec3(-1, -1, -1),
		glm::vec3(1, -1, -1),
		glm::vec3(1, 1, -1),
		glm::vec3(-1, 1, -1),
		// elülső lap
		glm::vec3(-1, -1, 1),
		glm::vec3(1, -1, 1),
		glm::vec3(1, 1, 1),
		glm::vec3(-1, 1, 1),
	}
	);

	m_SkyboxIndices.BufferData(
		std::vector<int>{
			// hátsó lap
			0, 1, 2,
			2, 3, 0,
			// elülső lap
			4, 6, 5,
			6, 4, 7,
			// bal
			0, 3, 4,
			4, 3, 7,
			// jobb
			1, 5, 2,
			5, 6, 2,
			// alsó
			1, 0, 4,
			1, 4, 5,
			// felső
			3, 2, 6,
			3, 6, 7,
	}
	);

	m_SkyboxVao.Init(
		{
			{ CreateAttribute<0, glm::vec3, 0, sizeof(glm::vec3)>, m_SkyboxPos },
		}, m_SkyboxIndices
	);

	glEnable(GL_TEXTURE_CUBE_MAP_SEAMLESS);

	m_skyboxTexture.AttachFromFile("assets/xpos.png", false, GL_TEXTURE_CUBE_MAP_POSITIVE_X);
	m_skyboxTexture.AttachFromFile("assets/xneg.png", false, GL_TEXTURE_CUBE_MAP_NEGATIVE_X);
	m_skyboxTexture.AttachFromFile("assets/ypos.png", false, GL_TEXTURE_CUBE_MAP_POSITIVE_Y);
	m_skyboxTexture.AttachFromFile("assets/yneg.png", false, GL_TEXTURE_CUBE_MAP_NEGATIVE_Y);
	m_skyboxTexture.AttachFromFile("assets/zpos.png", false, GL_TEXTURE_CUBE_MAP_POSITIVE_Z);
	m_skyboxTexture.AttachFromFile("assets/zneg.png", true, GL_TEXTURE_CUBE_MAP_NEGATIVE_Z);

	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

	glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
}

void CMyApp::InitShaders()
{
	m_program.AttachShaders({
		{ GL_VERTEX_SHADER, "myVert.vert"},
		{ GL_FRAGMENT_SHADER, "myFrag.frag"}
	});

	m_program.BindAttribLocations({
		{ 0, "vs_in_pos" },				
		{ 1, "vs_in_norm" },		
		{ 2, "vs_in_tex" },				
	});

	m_program.LinkProgram();

	suz_shader.Init(
		{
			{ GL_VERTEX_SHADER, "suzVert.vert"},
			{ GL_FRAGMENT_SHADER, "suzFrag.frag"}
		},
		{
			{ 0, "vs_in_pos" },
			{ 1, "vs_in_norm" },
			{ 2, "vs_in_tex" },
		}

	);

	m_programSkybox.Init(
		{
			{ GL_VERTEX_SHADER, "skybox.vert" },
			{ GL_FRAGMENT_SHADER, "skybox.frag" }
		},
		{
			{ 0, "vs_in_pos" },		
		}
	);
}

bool CMyApp::Init()
{
	
	glClearColor(0.125f, 0.25f, 0.5f, 1.0f);

	glEnable(GL_CULL_FACE); 
	glEnable(GL_DEPTH_TEST); 

	InitShaders();
	InitSkyBox();


	m_woodTexture.FromFile("assets/wood.jpg");
	m_suzanneTexture.FromFile("assets/leaves.jpg");
	cowTexture.FromFile("assets/milka.jpg");
	ironTexture.FromFile("assets/iron.jpg");

	
	m_mesh = std::unique_ptr<Mesh>(ObjParser::parse("assets/Suzanne.obj"));
	m_mesh->initBuffers();

	cow_mesh= std::unique_ptr<Mesh>(ObjParser::parse("assets/cow.obj"));
	cow_mesh->initBuffers();
	
	// kamera
	m_camera.SetProj(glm::radians(60.0f), 1280.0f /1024.0f, 0.01f, 1000.0f);

	return true;
}

void CMyApp::Clean()
{
}

void CMyApp::Update()
{
	static Uint32 last_time = SDL_GetTicks();
	float delta_time = (SDL_GetTicks() - last_time) / 1000.0f;


	


	
	
	if (dolly_zoom_enabled) {
		

		if (changed_dolly_focus) {
			float theta = glm::radians(prev_dolly_fov);
			float f = 1.0f / tan(theta / 2.0f);
			float fNew = f * (dolly_focus  ) / (prev_dolly_focus );
			float thetaNew = atan(1.0f / fNew) * 2.0f;
			dolly_fov = 180.0f * thetaNew / M_PI;
			
			std::cout << dolly_focus << std::endl;
		
		}
		else if (changed_dolly_fov) {
			dolly_focus= prev_dolly_focus *( glm::tan(glm::radians(prev_dolly_fov/2.f)) / glm::tan(glm::radians(dolly_fov/2.f))) *2.f;
			

		}

		m_camera.SetView(glm::vec3(dolly_focus, 0, 0 ), glm::vec3(0,0 , 0), glm::vec3(0, 1, 0));	
	}
	m_camera.SetProj(glm::radians(dolly_fov), 1280.0f / 1024.0f, 0.01f, 1000.0f);
	
	m_camera.Update(delta_time);
	last_time = SDL_GetTicks();
}

void CMyApp::Render()
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glm::mat4 viewProj = m_camera.GetViewProj();

	//Suzanne
	glm::mat4 suzanneWorld = glm::mat4(1.0f)*glm::translate(glm::vec3(20,0,15));
	suz_shader.Use();
	suz_shader.SetTexture("texImage", 0, m_suzanneTexture);
	suz_shader.SetTexture("texImageIron", 1, ironTexture);
	suz_shader.SetUniform("MVP", viewProj * suzanneWorld);
	suz_shader.SetUniform("world", suzanneWorld);
	suz_shader.SetUniform("worldIT", glm::inverse(glm::transpose(suzanneWorld)));
	suz_shader.SetUniform("uvVal", uvVal);
	m_mesh->draw();
	suz_shader.Unuse();

	//cow
	glm::mat4 cowWorld = glm::mat4(1.0f)*glm::rotate(80.f,glm::vec3(1,0,0));
	m_program.Use();
	m_program.SetTexture("texImage", 0, cowTexture);
	m_program.SetUniform("MVP", viewProj * cowWorld);
	m_program.SetUniform("world", cowWorld);
	m_program.SetUniform("worldIT", glm::inverse(glm::transpose(cowWorld)));
	cow_mesh->draw();
	m_program.Unuse();


	GLint prevDepthFnc;
	glGetIntegerv(GL_DEPTH_FUNC, &prevDepthFnc);


	glDepthFunc(GL_LEQUAL);

	m_SkyboxVao.Bind();
	m_programSkybox.Use();
	m_programSkybox.SetUniform("MVP", viewProj * glm::translate( m_camera.GetEye()) );
	
	
	glActiveTexture(GL_TEXTURE0);
	glBindTexture(GL_TEXTURE_CUBE_MAP, m_skyboxTexture);
	glUniform1i(m_programSkybox.GetLocation("skyboxTexture"), 0);

	glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, nullptr);
	m_programSkybox.Unuse();


	glDepthFunc(prevDepthFnc);


	//ImGui::ShowTestWindow();
	
	if (ImGui::Begin("Game GUI")) {
		ImGui::SliderFloat("Suzi uv settings", &uvVal,0,3);

		if (dolly_zoom_enabled) {
			changed_dolly_focus=ImGui::SliderFloat("Dolly focus", &dolly_focus, 25, 1000);
		}
		changed_dolly_fov=ImGui::SliderFloat("Dolly fov", &dolly_fov, 0, 179);
		ImGui::Checkbox("Dolly enable", &dolly_zoom_enabled);
		if (ImGui::Button("Set camera")) {
			glm::vec3 pos = m_camera.GetEye();
			dolly_focus = glm::sqrt((pos.x * pos.x) + (pos.y * pos.y) + (pos.z * pos.z));
		}


	}
	ImGui::End();

	if (!dolly_zoom_enabled) {
		prev_dolly_fov = dolly_fov;
	}


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
