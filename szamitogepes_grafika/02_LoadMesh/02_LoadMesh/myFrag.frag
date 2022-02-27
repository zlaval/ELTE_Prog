#version 130

// pipeline-ból bejövõ per-fragment attribútumok
in vec2 vs_out_tex0;
in vec3 vs_out_norm;
in vec3 vs_out_pos;

// kimenõ érték - a fragment színe
out vec4 fs_out_col;

uniform sampler2D texture;
uniform sampler2D texture2;

uniform vec3 light_pos = vec3(0,5,5);
uniform vec3 eye_Pos = vec3(1,1,1);

// fénytulajdonságok: ambiens, diffúz, ...
vec4 La = vec4(0.8);
vec4 Ld = vec4(0.8);
vec4 Ls = vec4(1);

// anyagtulajdonságok: ambiens, diffúz, ...
vec4 Ka = vec4(1);
vec4 Kd = vec4(1);
vec4 Ks = vec4(1);

void main()
{
	vec4 ambient = La*Ka;

	vec3 normal = normalize(vs_out_norm);
	vec3 toLight = normalize(light_pos - vs_out_pos);

	float di = clamp(dot(normal,toLight), 0.0f, 1.0f);

	vec4 diffuse = Ld * Kd * di;

	vec3 toEye = normalize(eye_Pos - vs_out_pos);
	vec3 h = normalize(toLight + toEye);
	float si = pow(clamp(dot(h,normal), 0.0f, 1.0f), 25);

	vec4 specular = Ls * Ks * si;

	fs_out_col = (specular + diffuse + ambient) * texture2D(texture, vs_out_tex0.st);
}