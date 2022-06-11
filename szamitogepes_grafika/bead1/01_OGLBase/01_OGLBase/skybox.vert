#version 330 core

// VBO-ból érkező változók
in vec3 vs_in_pos;

// a pipeline-ban tovább adandó értékek
out vec3 vs_out_pos;

// shader külső paraméterei - most a három transzformációs mátrixot külön-külön vesszük át
uniform mat4 MVP;

void main()
{
	gl_Position = (MVP * vec4( vs_in_pos, 1 )).xyww;	// [x,y,w,w] => homogén osztás után [x/w, y/w, 1]

	vs_out_pos = vs_in_pos;
}