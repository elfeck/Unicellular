//@section struct
struct Global_light {
	vec4 position;
	vec4 function;
};

//@section decl
float light_parabolic(in vec2 dist, in Global_light light);
float light_gaussian(in vec2 dist, in Global_light light);
float light_para_gauss(in vec2 dist, in Global_light light);

//@section parabolic
float light_parabolic(in vec2 dist, in Global_light light) {
	float size = light.function.x;
	float intensity = light.function.y;
	return max(light_minimum, 1 - (pow(dist.x / size, 2) + pow(dist.y / size, 2))) * intensity;
}

//@section gaussian
float light_gaussian(in vec2 dist, in Global_light light) {
	float size = light.function.x;
	float intensity = light.function.y;
	return (1 / (1 + light_minimum)) * exp(-(pow(dist.x / size, 2) + pow(dist.y / size, 2))) * intensity + light_minimum;
}

//@section para_gauss
float light_para_gauss(in vec2 dist, in Global_light light) {
	return light_parabolic(dist, light) + light_gaussian(dist, light);
}